package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.util.ArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.JogadorComSorte;
import com.uece.poo.jogo_de_tabuleiro.model.JogadorNormal;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChoosePlayerController {

    @FXML
    private Button voltarButton;
    @FXML
    private AnchorPane choosePLayerAnchorPane;
    private boolean modoDebug;
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private int quantidadeDeJogadores;

    public void carregar(boolean modoDebug, int quantidadeDeJogadores) {
        this.modoDebug = modoDebug;
        this.quantidadeDeJogadores = quantidadeDeJogadores;
        for (int i = 1; i <= 6; i++) {
            if (i <= quantidadeDeJogadores) {
                Pane jogadorAtualPane = (Pane) choosePLayerAnchorPane.lookup("#jogador" + i);
                ComboBox tipoJogadorComboBox = (ComboBox) jogadorAtualPane.lookup("#tipoJogador" + i);
                ObservableList tiposJogador = FXCollections.observableArrayList();
                jogadorAtualPane.setDisable(false);
                tiposJogador.add("Normal");
                tiposJogador.add("Com Sorte");
                tiposJogador.add("Azarado");
                tipoJogadorComboBox.setItems(tiposJogador);
            }
        }
    }

    public void buildTabletop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/tabuleiro.fxml"));
        Parent root = loader.load();

        TabuleiroController tabuleiroController = loader.getController();

        criarJogadores();

        Tabuleiro tabuleiro = new Tabuleiro(jogadores);
        tabuleiroController.carregarTabuleiro(tabuleiro, jogadores, modoDebug);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void criarJogadores() {
        jogadores.clear();
        for (int i = 1; i <= 6; i++) {
            if (i <= quantidadeDeJogadores) {
                Pane jogadorAtualPane = (Pane) choosePLayerAnchorPane.lookup("#jogador" + i);
                if (!jogadorAtualPane.isDisabled()) {
                    TextField jogadorAtualNome = (TextField) jogadorAtualPane.lookup("#jogadorNome" + i);
                    ColorPicker jogadorAtualCor = (ColorPicker) jogadorAtualPane.lookup("#jogadorCor" + i);
                    ComboBox jogadorAtualTipo = (ComboBox) jogadorAtualPane.lookup("#tipoJogador" + i);

                    switch (String.valueOf(jogadorAtualTipo.getValue())) {
                        case "Normal" ->
                            jogadores.add(new JogadorNormal(String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText()));
                        case "Com Sorte" ->
                            jogadores.add(new JogadorComSorte(String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText()));
                        case "Azarado" ->
                            jogadores.add(new JogadorAzarado(String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText()));
                        default ->
                            System.out.println("Não reconhecido. " + String.valueOf(jogadorAtualCor.getValue()) + ", " + jogadorAtualNome.getText());
                    }
                }
            }
        }
    }

    public void voltar(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
