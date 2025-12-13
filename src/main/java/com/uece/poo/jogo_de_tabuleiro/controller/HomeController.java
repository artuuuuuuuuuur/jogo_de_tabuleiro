package com.uece.poo.jogo_de_tabuleiro.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import com.uece.poo.jogo_de_tabuleiro.model.util.ExceptionModal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private ComboBox quantJogadoresComboBox;
    @FXML
    private Button modoNormalButton;
    @FXML
    private Button modoDebugButton;

    private ObservableList quantJogadoresList = FXCollections.observableArrayList();
    private boolean modoDebug;

    public void configTabuleiro(Event event) throws IOException {
        int valor = Integer.valueOf(quantJogadoresComboBox.getValue().toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/config_tabuleiro.fxml"));
        Parent root = loader.load();
        ConfigTabuleiroController controller = loader.getController();

        controller.load(new Jogo(), modoDebug, valor);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToRules(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/tela_informacao.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 2; i <= 6; i++) {
            quantJogadoresList.add(i);
        }
        quantJogadoresComboBox.setItems(quantJogadoresList);
        modoDebugButton.setOnAction(e -> {
            modoDebug = true;
            try {
                configTabuleiro(e);
            } catch (IOException e1) {
            } catch (NullPointerException e2) {
                ExceptionModal.popUp("Preencha a quantidade de jogadores.");
            }
        });
        modoNormalButton.setOnAction(e -> {
            modoDebug = false;
            try {
                configTabuleiro(e);
            } catch (IOException e1) {
            } catch (NullPointerException e2) {
                ExceptionModal.popUp("Preencha a quantidade de Jogadores.");
            }
        });
    }
}
