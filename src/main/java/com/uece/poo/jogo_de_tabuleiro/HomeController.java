package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

    public void goToRules(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/tela_informacao.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void exceptionModal() {
        Platform.runLater(() -> {
            Stage alerta = new Stage();
            alerta.setTitle("Erro");

            Label message = new Label("Preencha a quantidade de jogadores.");
            message.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");

            Button ok = new Button("OK");
            ok.setOnAction(e -> alerta.close());

            VBox layout = new VBox(15, message, ok);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            alerta.setScene(new Scene(layout, 550, 150));
            alerta.initModality(Modality.APPLICATION_MODAL);
            alerta.showAndWait();
        });
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
            } catch (NullPointerException e2) {
                exceptionModal();
            }
        });
        modoNormalButton.setOnAction(e -> {
            modoDebug = false;
            try {
                buildChoosePlayer(e);
            } catch (IOException e1) {
            } catch (NullPointerException e2) {
                exceptionModal();
            }
        });

    }
}
