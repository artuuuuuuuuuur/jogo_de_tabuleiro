package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.lang.reflect.Array;
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
    private Label currentPlayerDice1;

    public void carregarTabuleiro(Tabuleiro tabuleiro, ArrayList<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        inicializar();
    }

    private void setColors() {
        if (!(jogadores.get(0) == null)) {
            player0Color.setText(jogadores.get(0).getCor());
        }
        if (!(jogadores.get(1) == null)) {
            player1Color.setText(jogadores.get(1).getCor());
        }
        if (!(jogadores.get(2) == null)) {
            player2Color.setText(jogadores.get(2).getCor());
        }
        if (!(jogadores.get(3) == null)) {
            player3Color.setText(jogadores.get(3).getCor());
        }
    }

    private void inicializar() {
        setColors();
        new Thread(() -> {  // Thread separada para não travar o JavaFX
            int i = 0;

            while (!partidaTerminada) {
                for (Jogador jogador : jogadores) {
                    for (Casa casa : tabuleiro.getCasas()) {
                        for (Jogador jogadorDaCasa : casa.getJogadores()) {
                            if (jogador.getNome().equals(jogadorDaCasa.getNome())) {
                                jogadorDaCasa.rolarDados();

                                // Atualiza UI de forma segura:
                                Platform.runLater(() -> {
                                    currentPlayerDice1.setText("" + jogadorDaCasa.getDados()[0] + " + " + jogadorDaCasa.getDados()[1] + " = " + (jogadorDaCasa.getDados()[1] + jogadorDaCasa.getDados()[0]));
                                });

                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (i < 5) {
                                    i++;
                                } else {
                                    partidaTerminada = true;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Partida finalizada!");
        }).start(); // inicia a thread separada
    }

}
