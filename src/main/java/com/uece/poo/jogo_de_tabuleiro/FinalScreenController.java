package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FinalScreenController {

    private List<Jogador> jogadores;
    private boolean modoDebug;

    public void carregar(List<Jogador> jogadores, boolean modoDebug) {
        this.jogadores = jogadores;
        this.modoDebug = modoDebug;
    }
    

    public void goToMenu(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
