package com.uece.poo.jogo_de_tabuleiro;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import com.uece.poo.jogo_de_tabuleiro.model.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private volatile boolean partidaTerminada = false;
    private final Semaphore pauseSemaphore = new Semaphore(0);
    private Jogador jogadorVencedor;
    private final Map<Jogador, Circle> jogadoresIcons = new HashMap<>();
    private boolean debugMode;

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

    public void carregarTabuleiro(Tabuleiro tabuleiro, List<Jogador> jogadores, boolean debugMode) {
        this.tabuleiro = tabuleiro;
        this.jogadores = new CopyOnWriteArrayList<>(jogadores);

        this.debugMode = debugMode;

        for (Jogador jogador : this.jogadores) {
            Circle jogadorCircle = new Circle(10);
            jogadorCircle.setFill(Paint.valueOf(jogador.getCor()));
            jogadoresIcons.put(jogador, jogadorCircle);
        }

        inicializar();
    }

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
            atualizarCasas();
        });
    }

    private String infoJogador(int idx) {
        Jogador j = jogadores.get(idx);
        return "Cor: " + j.getCor() + " | Nome: " + j.getNome() + " | Posição: " + j.getPosicao();
    }

    private void atualizarCasas() {
        for (Casa casa : tabuleiro.getCasas()) {
            casa.clearJogadores();
        }

        for (Jogador jogador : jogadores) {
            Casa casa = tabuleiro.getCasa(jogador.getPosicao());
            if (casa != null) {
                casa.addJogador(jogador);
            }
        }

        for (Jogador jogador : jogadores) {
            Pane casaAtual = (Pane) tabuleiroPane.lookup("#casa" + jogador.getPosicao());
            if (casaAtual != null) {
                Circle circle = jogadoresIcons.get(jogador);
                if (!casaAtual.getChildren().contains(circle)) {
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
                        final Jogador current = jogador;
                        Platform.runLater(() -> {
                            currentPlayer.setText("Vez de " + current.getNome());
                            jogarDadosButton.setDisable(false);
                        });
                        
                        try {
                            pauseSemaphore.acquire();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }

                        if (debugMode) {
                            CompletableFuture<Integer> resultadoDebug = new CompletableFuture<>();

                            Platform.runLater(() -> {
                                Stage popup = new Stage();
                                popup.setTitle("Modo Debug - Valor dos Dados");

                                Label label = new Label("Digite o valor que deseja andar: ");
                                TextField input = new TextField();
                                input.setPromptText("ex: 7");

                                Button confirmar = new Button("Confirmar");

                                confirmar.setOnAction(e -> {
                                    try {
                                        int valor = Integer.parseInt(input.getText());
                                        resultadoDebug.complete(valor);
                                        popup.close();
                                    } catch (NumberFormatException ex) {
                                        input.setStyle("-fx-border-color: red;");
                                    }
                                });

                                VBox layout = new VBox(10, label, input, confirmar);
                                layout.setAlignment(Pos.CENTER);
                                layout.setPadding(new Insets(20));

                                popup.setScene(new Scene(layout, 300, 150));
                                popup.initModality(Modality.APPLICATION_MODAL);
                                popup.show();
                            });

                            int tempValorDados;
                            try {
                                tempValorDados = resultadoDebug.get();
                            } catch (Exception e) {
                                e.printStackTrace();
                                tempValorDados = 7;
                            }
                            final int valorDados = tempValorDados;

                            jogador.setPosicao(jogador.getPosicao() + valorDados);

                            Platform.runLater(() -> {
                                currentPlayerDices.setText("DEBUG: " + valorDados);
                                atualizarStats();
                            });

                            atualizarCasasSync();

                            Casa casaAtual = tabuleiro.getCasa(jogador.getPosicao());
                            if (casaAtual != null) {
                                casaAtual.executarAcaoEspecial(tabuleiro);
                            }

                            Platform.runLater(this::atualizarStats);

                        } else {
                            jogador.jogar();
                            Platform.runLater(() -> {
                                currentPlayerDices.setText(jogador.getDados()[0] + " + " + jogador.getDados()[1]
                                        + " = " + (jogador.getDados()[0] + jogador.getDados()[1]));
                                atualizarStats();
                            });

                            atualizarCasasSync();

                            Casa casaAtual = tabuleiro.getCasa(jogador.getPosicao());
                            if (casaAtual != null) {
                                casaAtual.executarAcaoEspecial(tabuleiro);
                            }

                            Platform.runLater(this::atualizarStats);
                        }

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

                        if (jogador.isDadosIguais() && !debugMode) {
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

                            jogador.jogar();
                            Platform.runLater(this::atualizarStats);

                            atualizarCasasSync();

                            Casa casaAtual = tabuleiro.getCasa(jogador.getPosicao());
                            if (casaAtual != null) {
                                casaAtual.executarAcaoEspecial(tabuleiro);
                            }

                            Platform.runLater(this::atualizarStats);

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
                    } else {
                        jogador.setAtivo(true);
                        System.out.println(jogador.getNome() + " agora pode jogar.");
                    }
                }
            }
        }, "Thread-Jogo").start();
    }

    public void resume() {
        pauseSemaphore.release();
    }

    private void atualizarCasasSync() {
        // limpar jogadores de todas as casas
        for (Casa casa : tabuleiro.getCasas()) {
            casa.clearJogadores(); // você já tem esse método
        }

        // repopular de acordo com a posição atual dos jogadores
        for (Jogador jogador : jogadores) {
            Casa casa = tabuleiro.getCasa(jogador.getPosicao());
            if (casa != null) {
                casa.addJogador(jogador);
            }
        }
    }

    private void inicializar() {
        atualizarStats();
        jogarPartida();
    }
}
