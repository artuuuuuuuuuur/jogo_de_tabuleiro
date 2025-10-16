package com.uece.poo.jogo_de_tabuleiro;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.uece.poo.jogo_de_tabuleiro.model.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private ArrayList<Jogador> jogadores;
    private boolean partidaTerminada = false;
    private final Semaphore pauseSemaphore = new Semaphore(0);
    private Jogador jogadorVencedor;

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

    public void carregarTabuleiro(Tabuleiro tabuleiro, ArrayList<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        inicializar();
    }

    // Atualizar para quantidade dinâmica
    private void atualizarStats() {
        Platform.runLater(() -> {
            if (!(jogadores.get(0) == null)) {
                player0Color.setText("Cor: " + jogadores.get(0).getCor() + " | Nome: " + jogadores.get(0).getNome()
                        + " | Posição: " + jogadores.get(0).getPosicao());
            }
            if (!(jogadores.get(1) == null)) {
                player1Color.setText("Cor: " + jogadores.get(1).getCor() + " | Nome: " + jogadores.get(1).getNome()
                        + " | Posição: " + jogadores.get(1).getPosicao());

            }
            if (!(jogadores.get(2) == null)) {
                player2Color.setText("Cor: " + jogadores.get(2).getCor() + " | Nome: " + jogadores.get(2).getNome()
                        + " | Posição: " + jogadores.get(2).getPosicao());
            }
            if (!(jogadores.get(3) == null)) {
                player3Color.setText("Cor: " + jogadores.get(3).getCor() + " | Nome: " + jogadores.get(3).getNome()
                        + " | Posição: " + jogadores.get(3).getPosicao());
            }
            
            for (Casa casa : tabuleiro.getCasas()) {
                for (Jogador jogador : jogadores) {
                    if(jogador.getPosicao() == casa.getIndex()) {
                        casa.addJogador(jogador);
                    } else {
                        casa.removeJogador(jogador);
                    }
                }
            }
        });
    }

    private void jogarPartida() {
        new Thread(() -> {
            while (!partidaTerminada) {
                for (Jogador jogador : jogadores) {
                    Platform.runLater(() -> {
                        currentPlayer.setText("Vez de " + jogador.getNome());
                        jogarDadosButton.setDisable(false);
                    });
                    try {
                        pauseSemaphore.acquire();
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                    jogar(jogador);
                    atualizarStats();

                    if (jogador.getPosicao() == 40) {
                        partidaTerminada = true;
                        jogadorVencedor = jogador;
                        return;
                    }

                }
            }
            System.out.println("Partida terminada. " + jogadorVencedor.getNome() + " venceu!");
        }).start();
    }

    public void resume() {
        pauseSemaphore.release();
    }

    private void jogar(Jogador jogador) {
        Platform.runLater(() -> {
            jogarDadosButton.setDisable(true);
        });

        jogador.jogar();

        Platform.runLater(() -> {
            currentPlayerDices.setText(jogador.getDados()[0] + " + " + jogador.getDados()[1] + " = " + (jogador.getDados()[1] + jogador.getDados()[0]));
        });

        atualizarStats();

        Platform.runLater(() -> {
            jogarDadosButton.setDisable(false);
        });

        if (jogador.isDadosIguais()) {
            try {
                pauseSemaphore.acquire();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            jogador.jogar();
            Platform.runLater(() -> {
                currentPlayerDices.setText(jogador.getDados()[0] + " + " + jogador.getDados()[1] + " = " + (jogador.getDados()[1] + jogador.getDados()[0]));
            });
            atualizarStats();
        }
    }

    private void inicializar() {
        atualizarStats();
        jogarPartida();
    }

}
