package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Jogador;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FinalScreenController {

    private List<Jogador> jogadores;

    public void carregar(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public void goToMenu(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/home.fxml"));
        Parent root = loader.load();
        FinalScreenController finalScreenController = loader.getController();
        finalScreenController.carregar(jogadores);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
