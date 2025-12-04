package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorSortudo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorNormal;
import com.uece.poo.jogo_de_tabuleiro.model.util.ExceptionModal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoosePlayerController {

    @FXML
    private Button voltarButton;
    @FXML
    private AnchorPane choosePLayerAnchorPane;
    private boolean modoDebug;
    private List<Jogador> jogadores = new CopyOnWriteArrayList<>();
    private Set<String> coresEscolhidas = new HashSet<>();
    private int quantidadeDeJogadores;
    private boolean jogadoresNormais = false;
    private boolean jogadoresComSorte = false;
    private boolean jogadoresAzarados = false;

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

        for (Jogador jogador : jogadores) {
            if (!coresEscolhidas.add(jogador.getCor())) {
                ExceptionModal.popUp("Dois jogadores não podem ter a mesma cor!");
                return;
            }
        }

        int count = 0;

        if (jogadoresNormais) {
            count++;
        }
        if (jogadoresComSorte) {
            count++;
        }
        if (jogadoresAzarados) {
            count++;
        }

        if (count < 2) {
            ExceptionModal.popUp("É necessário que ao menos dois jogadores sejam de tipos diferentes");
            return;
        }

        //Tabuleiro tabuleiro = new Tabuleiro(jogadores);
        //tabuleiroController.carregarTabuleiro(tabuleiro, jogadores, modoDebug);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    private void criarJogadores() throws IOException {
        jogadores.clear();
        coresEscolhidas.clear();
        for (int i = 1; i <= 6; i++) {
            if (i <= quantidadeDeJogadores) {
                Pane jogadorAtualPane = (Pane) choosePLayerAnchorPane.lookup("#jogador" + i);
                if (!jogadorAtualPane.isDisabled()) {
                    TextField jogadorAtualNome = (TextField) jogadorAtualPane.lookup("#jogadorNome" + i);
                    ColorPicker jogadorAtualCor = (ColorPicker) jogadorAtualPane.lookup("#jogadorCor" + i);
                    ComboBox jogadorAtualTipo = (ComboBox) jogadorAtualPane.lookup("#tipoJogador" + i);

                    if (jogadorAtualNome.getText().equals("")) {
                        jogadorAtualNome.setText("Jogador " + i);
                    }

                    switch (String.valueOf(jogadorAtualTipo.getValue())) {
                        case "Normal" -> {
                            jogadores.add(new JogadorNormal(String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText()));
                            jogadoresNormais = true;
                        }
                        case "Com Sorte" -> {
                            jogadores.add(new JogadorSortudo(String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText()));
                            jogadoresComSorte = true;
                        }
                        case "Azarado" -> {
                            jogadores.add(new JogadorAzarado(String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText()));
                            jogadoresAzarados = true;
                        }
                        default -> {
                            System.out.println("Não reconhecido. " + String.valueOf(jogadorAtualCor.getValue()) + ", "
                                    + jogadorAtualNome.getText());
                            jogadoresAzarados = false;
                            jogadoresComSorte = false;
                            jogadoresNormais = false;
                            return;
                        }
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
