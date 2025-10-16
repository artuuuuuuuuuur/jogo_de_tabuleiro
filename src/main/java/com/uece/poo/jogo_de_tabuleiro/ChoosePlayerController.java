package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.util.ArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.JogadorComSorte;
import com.uece.poo.jogo_de_tabuleiro.model.JogadorNormal;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.fxml.FXMLLoader;

public class ChoosePlayerController {

    public void buildTabletop() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/tabuleiro.fxml"));

        TabuleiroController tabuleiroController = loader.getController();

        ArrayList<Jogador> jogadores = new ArrayList<>();
        jogadores.add(new JogadorNormal("azul"));
        jogadores.add(new JogadorNormal("amarelo"));
        jogadores.add(new JogadorAzarado("verde"));
        jogadores.add(new JogadorComSorte("rosa"));

        Tabuleiro tabuleiro = new Tabuleiro(jogadores);
        tabuleiroController.carregarTabuleiro(tabuleiro);

        App.setRoot("tabuleiro");
    }
}
