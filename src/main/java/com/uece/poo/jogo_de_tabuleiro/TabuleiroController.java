package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private ArrayList<Jogador> jogadores;

    @FXML
    private Label player0Color;
    @FXML
    private Label player1Color;
    @FXML
    private Label player2Color;
    @FXML
    private Label player3Color;

    public void carregarTabuleiro(Tabuleiro tabuleiro, ArrayList<Jogador> jogadores) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        inicializar();
    }

    public void inicializar() {
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

}
