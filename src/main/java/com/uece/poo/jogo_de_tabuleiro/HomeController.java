package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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

    public void buildChoosePlayer(Event event) throws IOException {
        int valor = Integer.valueOf(quantJogadoresComboBox.getValue().toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/choose_player.fxml"));
        Parent root = loader.load();
        ChoosePlayerController choosePlayerController = loader.getController();

        choosePlayerController.carregar(modoDebug, valor);

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
                buildChoosePlayer(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        modoNormalButton.setOnAction(e -> {
            modoDebug = false;
            try {
                buildChoosePlayer(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }
}
