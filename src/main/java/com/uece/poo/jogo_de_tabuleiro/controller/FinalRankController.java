package com.uece.poo.jogo_de_tabuleiro.controller;

import java.io.IOException;
import java.util.List;

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

    @FXML
    private Button nextButton;
    @FXML
    private GridPane rankGridPane;
    @FXML
    private AnchorPane rankAnchorPane;
    private List<Jogador> jogadores;
    private boolean modoDebug;

    public void carregar(List<Jogador> jogadores, boolean modoDebug) {
        this.jogadores = jogadores;
        this.modoDebug = modoDebug;
        jogadores.sort((j1, j2) -> Integer.compare(j2.getPosicao(), j1.getPosicao()));
        nextButton.setOnAction(e -> {
            try {
                goToFinalScreen();
            } catch (IOException e1) {
                ExceptionModal.popUp(e1.getMessage());
            }
        });
        int i = 1;
        for (Jogador jogador : jogadores) {
            Label jogadorNome = (Label) rankGridPane.lookup("#playerNome" + i);
            Label jogadorPosicao = (Label) rankGridPane.lookup("#playerPosicao" + i);
            Label jogadorJogadas = (Label) rankGridPane.lookup("#playerJogadas" + i);
            jogadorNome.setText(jogador.getNome());
            jogadorPosicao.setText(String.valueOf(jogador.getPosicao()));
            jogadorJogadas.setText(String.valueOf(jogador.getVezesJogadas()));
            i++;
        }
    }

    private void goToFinalScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/final_screen.fxml"));
        Parent root = loader.load();
        FinalScreenController finalScreenController = loader.getController();
        finalScreenController.carregar(jogadores, modoDebug);
        Stage stage = (Stage) rankAnchorPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
