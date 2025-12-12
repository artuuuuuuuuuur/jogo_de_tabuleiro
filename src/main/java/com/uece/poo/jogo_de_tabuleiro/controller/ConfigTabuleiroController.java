package com.uece.poo.jogo_de_tabuleiro.controller;

import com.uece.poo.jogo_de_tabuleiro.App;
import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.*;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigTabuleiroController {
    @FXML
    Spinner<Integer> quantCasasSpinner;
    @FXML
    VBox casasEspeciaisVBox;
    @FXML
    static ComboBox<String> casaTypeComboBox;
    @FXML
    Spinner<Integer> indexSpinner;
    private Jogo jogo;
    private boolean modoDebug;
    private HashMap<Integer, Class<? extends Casa>> casasEspeciais;
    private HashMap<String, Class<? extends Casa>> casasEspeciaisStrings;
    private ArrayList<String> descricaoCasas;

    public void load(Jogo jogo, boolean modoDebug) {
        this.jogo = jogo;
        this.modoDebug = modoDebug;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1);
        quantCasasSpinner.setValueFactory(valueFactory);
        quantCasasSpinner.setEditable(true);
        indexSpinner.setValueFactory(valueFactory);
        indexSpinner.setEditable(true);
        // Mover para uma class
        descricaoCasas = new ArrayList<>();
        descricaoCasas.add("Voltar 3 casas");
        descricaoCasas.add("Andar 3 casas");
        descricaoCasas.add("Jogar novamente");
        descricaoCasas.add("Prisão");
        descricaoCasas.add("Trocar de lugar");
        descricaoCasas.add("Mudar tipo de jogador");
        descricaoCasas.add("Voltar ao início");
        casasEspeciaisStrings.put(descricaoCasas.get(0), CasaAzar.class);
        casasEspeciaisStrings.put(descricaoCasas.get(1), CasaSorte.class);
        casasEspeciaisStrings.put(descricaoCasas.get(2), CasaJogaDeNovo.class);
        casasEspeciaisStrings.put(descricaoCasas.get(3), CasaPrisao.class);
        casasEspeciaisStrings.put(descricaoCasas.get(4), CasaReversa.class);
        casasEspeciaisStrings.put(descricaoCasas.get(5), CasaSurpresa.class);
        casasEspeciaisStrings.put(descricaoCasas.get(6), CasaVoltarInicio.class);
    }

    public void abrirTelaDeEscolha(Event event) throws IOException {
        Stage stage = new Stage();
        Scene scene;
        scene = new Scene(new FXMLLoader(App.class.getResource("add_casa_especial.fxml")).load());
        inicializarElementos();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    private void inicializarElementos() {
        for (String descricaoCasa : descricaoCasas) {
            casaTypeComboBox.getItems().add(descricaoCasa);
        }
    }

    public void addCasaEspecial() {
        try {
            validarParametros();
            casasEspeciais.put(indexSpinner.getValue(), casasEspeciaisStrings.get(casaTypeComboBox.getValue()));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    private void validarParametros() {
        if(quantCasasSpinner.getValue() <= 0) {
            throw new IllegalArgumentException("Insira pelo menos 1 casa.");
        }
        if(casaTypeComboBox.getValue().equals("") || casaTypeComboBox.getValue() == null) {
            throw new IllegalArgumentException("Escolha uma ação especial.");
        }
    }

    public void iniciarPartida(){}
    public void voltar() {}
}
