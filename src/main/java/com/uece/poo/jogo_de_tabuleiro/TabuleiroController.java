package com.uece.poo.jogo_de_tabuleiro;

import java.util.ArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private ArrayList<Jogador> jogadores;
    private boolean partidaTerminada = false;

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

    public void carregarTabuleiro(Tabuleiro tabuleiro, ArrayList<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        inicializar();
    }

    // Atualizar para quantidade dinâmica
    private void atualizarStats() {
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
    }
    
    private void jogarPartida() {
        new Thread(() -> {
            while (!partidaTerminada) {
                for (Jogador jogador : jogadores) {

                    jogador.jogar();

                    while (jogador.isDadosIguais()) {
                        jogador.jogar();
                    }

                    Platform.runLater(() -> {
                        currentPlayerDices.setText("" + jogador.getDados()[0] + " + " + jogador.getDados()[1] + " = " + (jogador.getDados()[1] + jogador.getDados()[0]));
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        atualizarStats();
                    });

                    if (jogador.getPosicao() == 40) {
                        partidaTerminada = true;
                        return;
                    }

                }
            }
        }).start();
    }

    private void inicializar() {
        atualizarStats();
        jogarPartida();
    }

}
