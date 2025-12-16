package com.uece.poo.jogo_de_tabuleiro.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorFactory;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorSortudo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorNormal;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.ExceptionModal;

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

    @FXML private Button voltarButton;
    @FXML private AnchorPane choosePLayerAnchorPane;

    private List<Jogador> jogadores = new CopyOnWriteArrayList<>();
    private Set<String> coresEscolhidas = new HashSet<>();
    private int quantidadeDeJogadores;
    private Jogo jogo;

    public void carregar(Jogo jogo, int quantidadeDeJogadores) {
        this.jogo = jogo;
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

    @FXML public void buildTabletop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/tabuleiro.fxml"));
        Parent root = loader.load();

        TabuleiroController controller = loader.getController();

        criarJogadores();

        try {
            jogo.configJogadores(jogadores);
        } catch (IllegalArgumentException e) {
            ExceptionModal.popUp(e.getMessage());
            return;
        }
        jogo.setListener(controller);
        controller.carregarTabuleiro(jogo);
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
                    Class<? extends Jogador> jogadorType;

                    if (jogadorAtualNome.getText().equals("")) {
                        jogadorAtualNome.setText("Jogador " + i);
                    }

                    switch (String.valueOf(jogadorAtualTipo.getValue())) {
                        case "Normal" -> jogadorType = JogadorNormal.class;
                        case "Com Sorte" -> jogadorType = JogadorSortudo.class;
                        case "Azarado" -> jogadorType = JogadorAzarado.class;
                        default -> {
                            ExceptionModal.popUp("Não reconhecido. " + jogadorAtualCor.getValue() + ", "
                                    + jogadorAtualNome.getText());
                            return;
                        }
                    }
                    jogadores.add(JogadorFactory.getJogador(jogadorType, String.valueOf(jogadorAtualCor.getValue()), jogadorAtualNome.getText(), jogo.getTabuleiro().getCasas().size()));
                }
            }
        }
    }

    @FXML public void voltar(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/config_tabuleiro.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        jogadores.clear();
    }

}
