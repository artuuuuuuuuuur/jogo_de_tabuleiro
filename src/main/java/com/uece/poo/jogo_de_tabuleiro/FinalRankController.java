package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.uece.poo.jogo_de_tabuleiro.model.Jogador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
                // TODO Auto-generated catch block
                e1.printStackTrace();
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
