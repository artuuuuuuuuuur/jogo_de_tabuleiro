package com.uece.poo.jogo_de_tabuleiro.controller;

import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.*;
import com.uece.poo.jogo_de_tabuleiro.model.util.CasasEspeciaisDescricao;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.util.HashMap;

public class AddCasaEspecialController {

    @FXML ComboBox<String> casaTypeComboBox;
    @FXML Spinner<Integer> indexSpinner;

    private ConfigTabuleiroController controller;

    public void inicializar(ConfigTabuleiroController controller, int quantCasas, HashMap<Integer, Class<? extends Casa>> casasEspeciais) {
        this.controller = controller;

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1);
        indexSpinner.setValueFactory(valueFactory);
        indexSpinner.setEditable(true);

        for (String descricaoCasa : CasasEspeciaisDescricao.getDescricoes()) {
            casaTypeComboBox.getItems().add(descricaoCasa);
        }
    }

    @FXML
    public void addCasaEspecial() {
        controller.addCasaEspecial(indexSpinner.getValue(), CasasEspeciaisDescricao.getClasse(casaTypeComboBox.getValue()));
        ((Stage) casaTypeComboBox.getScene().getWindow()).close();
    }
}
