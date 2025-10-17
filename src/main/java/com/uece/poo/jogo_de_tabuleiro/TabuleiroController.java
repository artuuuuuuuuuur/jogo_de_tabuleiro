package com.uece.poo.jogo_de_tabuleiro;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import com.uece.poo.jogo_de_tabuleiro.model.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private volatile boolean partidaTerminada = false;
    private final Semaphore pauseSemaphore = new Semaphore(0);
    private Jogador jogadorVencedor;
    private final Map<Jogador, Circle> jogadoresIcons = new HashMap<>();

    @FXML
    private Label player0Color;
    @FXML
    private Label player1Color;
    @FXML
    private Label player2Color;
    @FXML
    private Label player3Color;
    @FXML
    private Label currentPlayerDices;
    @FXML
    private Label currentPlayer;
    @FXML
    private Button jogarDadosButton;
    @FXML
    private GridPane tabuleiroPane;

    public void carregarTabuleiro(Tabuleiro tabuleiro, List<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        // usa CopyOnWriteArrayList para permitir substituições sem lançar CME
        this.jogadores = new CopyOnWriteArrayList<>(jogadores);

        // criar ícones (não precisa sincronizar aqui)
        for (Jogador jogador : this.jogadores) {
            Circle jogadorCircle = new Circle(10);
            jogadorCircle.setFill(Paint.valueOf(jogador.getCor()));
            jogadoresIcons.put(jogador, jogadorCircle);
        }

        inicializar();
    }

    // atualiza labels e posições visuais (sempre no JavaFX thread)
    private void atualizarStats() {
        Platform.runLater(() -> {
            // protege com tamanho mínimo
            if (!jogadores.isEmpty() && jogadores.get(0) != null) {
                player0Color.setText(infoJogador(0));
            }
            if (jogadores.size() > 1 && jogadores.get(1) != null) {
                player1Color.setText(infoJogador(1));
            }
            if (jogadores.size() > 2 && jogadores.get(2) != null) {
                player2Color.setText(infoJogador(2));
            }
            if (jogadores.size() > 3 && jogadores.get(3) != null) {
                player3Color.setText(infoJogador(3));
            }
            atualizarCasas(); // actualiza visualmente as casas
        });
    }

    private String infoJogador(int idx) {
        Jogador j = jogadores.get(idx);
        return "Cor: " + j.getCor() + " | Nome: " + j.getNome() + " | Posição: " + j.getPosicao();
    }

    // atualiza lista de jogadores em cada casa e ícones visuais
    private void atualizarCasas() {
        // preencher casas (faz no FX thread)
        for (Casa casa : tabuleiro.getCasas()) {
            // limpar e repopular a lista de jogadores da casa
            casa.clearJogadores(); // ideal: implemente esse método em Casa para limpar lista interna
        }

        // preencher casas de acordo com a posição atual dos jogadores
        for (Jogador jogador : jogadores) {
            Casa casa = tabuleiro.getCasa(jogador.getPosicao()); // ideal: método utilitário
            if (casa != null) {
                casa.addJogador(jogador);
            }
        }

        // atualizar ícones visuais na grid
        for (Jogador jogador : jogadores) {
            Pane casaAtual = (Pane) tabuleiroPane.lookup("#casa" + jogador.getPosicao());
            if (casaAtual != null) {
                Circle circle = jogadoresIcons.get(jogador);
                if (!casaAtual.getChildren().contains(circle)) {
                    // remover de outras casas se ainda estiver em alguma
                    // (opcional) — garantir que o circle esteja apenas na casa correta
                    casaAtual.getChildren().add(circle);
                }
            }
        }
    }

    private void jogarPartida() {
        new Thread(() -> {
            while (!partidaTerminada) {
                for (Jogador jogador : jogadores) {
                    if (partidaTerminada) {
                        break;
                    }

                    if (jogador.isAtivo()) {
                        // pedir interação ao usuário via UI
                        final Jogador current = jogador;
                        Platform.runLater(() -> {
                            currentPlayer.setText("Vez de " + current.getNome());
                            jogarDadosButton.setDisable(false);
                        });

                        // aguardar clique do usuário (resume())
                        try {
                            pauseSemaphore.acquire();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }

                        // LOGICA: jogar dados (no thread de jogo)
                        jogador.jogar();

                        // atualizar GUI com resultado
                        Platform.runLater(() -> {
                            currentPlayerDices.setText(jogador.getDados()[0] + " + " + jogador.getDados()[1]
                                    + " = " + (jogador.getDados()[0] + jogador.getDados()[1]));
                            atualizarStats();
                        });

                        executarAcoesEspeciais();
                        
                        // checar vitória
                        if (jogador.getPosicao() >= 40) {
                            jogador.setPosicao(40);
                            partidaTerminada = true;
                            jogadorVencedor = jogador;
                            Platform.runLater(() -> {
                                currentPlayer.setText("🏆 " + jogadorVencedor.getNome() + " venceu!");
                                jogarDadosButton.setDisable(true);
                                atualizarStats();
                            });
                            break;
                        }

                        // se tirou dados iguais: repetir turno (aguardar novo resume)
                        if (jogador.isDadosIguais()) {
                            Platform.runLater(() -> {
                                currentPlayer.setText(jogador.getNome() + " tirou dados iguais! Joga novamente!");
                                jogarDadosButton.setDisable(false);
                            });

                            try {
                                pauseSemaphore.acquire();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }

                            // repetir mecânica de jogar (sem duplicar muito código)
                            jogador.jogar();
                            Platform.runLater(this::atualizarStats);
                            executarAcoesEspeciais();

                            if (jogador.getPosicao() >= 40) {
                                jogador.setPosicao(40);
                                partidaTerminada = true;
                                jogadorVencedor = jogador;
                                Platform.runLater(() -> {
                                    currentPlayer.setText("🏆 " + jogadorVencedor.getNome() + " venceu!");
                                    jogarDadosButton.setDisable(true);
                                    atualizarStats();
                                });
                                break;
                            }
                        }
                    }
                }
            }
        }, "Thread-Jogo").start();
    }

    public void resume() {
        // liberar o semaphore diretamente (não via Platform.runLater)
        pauseSemaphore.release();
    }

    // executa ações especiais de todas as casas (lógica do jogo) — roda na thread de jogo
    private void executarAcoesEspeciais() {
        // chamar cada casa para executar sua ação
        for (Casa casa : tabuleiro.getCasas()) {
            casa.executarAcaoEspecial(tabuleiro);
        }
        // após aplicar efeitos, atualiza GUI
        Platform.runLater(this::atualizarStats);
    }

    private void inicializar() {
        atualizarStats();
        jogarPartida();
    }
}
