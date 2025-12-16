package com.uece.poo.jogo_de_tabuleiro.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.ExceptionModal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FinalRankController {

    @FXML GridPane rankGridPane;
    @FXML AnchorPane rankAnchorPane;

    private Jogo jogo;
    private List<Jogador> jogadores;

    public void carregar(Jogo jogo) {
        this.jogadores = new CopyOnWriteArrayList<>();
        this.jogo = jogo;
        jogadores.addAll(jogo.getJogadores());
        jogadores.sort((j1, j2) -> Integer.compare(j2.getPosicao(), j1.getPosicao()));
        int i = 1;
        for (Jogador jogador : jogadores) {
            Label jogadorNome = (Label) rankGridPane.lookup("#playerNome" + i);
            Label jogadorPosicao = (Label) rankGridPane.lookup("#playerPosicao" + i);
            Label jogadorJogadas = (Label) rankGridPane.lookup("#playerJogadas" + i);
            Label jogadorMoedas = (Label) rankGridPane.lookup("#playerMoedas" + i);
            jogadorNome.setText(jogador.getNome());
            jogadorPosicao.setText(String.valueOf(jogador.getPosicao()));
            jogadorJogadas.setText(String.valueOf(jogador.getVezesJogadas()));
            jogadorMoedas.setText(String.valueOf(jogador.getMoedas()));
            i++;
        }
    }

    @FXML private void goToTelaInicial() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) rankAnchorPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
