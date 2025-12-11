package com.uece.poo.jogo_de_tabuleiro.controller;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;

public class ConfigTabuleiroController {
    @FXML
    Spinner<Integer> quantCasasSpinner;
    @FXML
    VBox casasEspeciaisVBox;
    private Jogo jogo;
    private boolean modoDebug;

    public void load(Jogo jogo, boolean modoDebug) {
        this.jogo = jogo;
        this.modoDebug = modoDebug;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1);

        quantCasasSpinner.setValueFactory(valueFactory);
        quantCasasSpinner.setEditable(true);
    }

    public void addCasaEspecial() {}
    public void iniciarPartida(){}
    public void voltar() {}
}
