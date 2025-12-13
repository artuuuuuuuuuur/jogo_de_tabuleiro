package com.uece.poo.jogo_de_tabuleiro.controller;

import com.uece.poo.jogo_de_tabuleiro.App;
import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.*;
import com.uece.poo.jogo_de_tabuleiro.model.util.CasasEspeciaisDescricao;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.ExceptionModal;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ConfigTabuleiroController {
    @FXML
    Spinner<Integer> quantCasasSpinner;
    @FXML
    VBox casasEspeciaisVBox;

    private Jogo jogo;
    private boolean modoDebug;
    private HashMap<Integer, Class<? extends Casa>> casasEspeciais;
    private int quantJogadores;


    public void load(Jogo jogo, boolean modoDebug, int quantJogadores) {
        this.jogo = jogo;
        this.modoDebug = modoDebug;
        this.quantJogadores = quantJogadores;
        casasEspeciais = new HashMap<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1);
        quantCasasSpinner.setValueFactory(valueFactory);
        quantCasasSpinner.setEditable(true);

    }

    @FXML
    public void abrirTelaDeEscolha(Event event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("add_casa_especial.fxml"));
            Parent root = loader.load();
            AddCasaEspecialController controller = loader.getController();
            controller.inicializar(this, quantCasasSpinner.getValue(), casasEspeciais);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            ExceptionModal.popUp(e.toString());
        }
    }

    @FXML
    public void abrirEscolhaDeJogadores(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/choose_player.fxml"));
        Parent root = loader.load();

        ChoosePlayerController controller = loader.getController();

        try {
            jogo.configTabuleiro(quantCasasSpinner.getValue(), casasEspeciais, modoDebug);
        } catch (IllegalArgumentException e) {
            ExceptionModal.popUp(e.getMessage());
            return;
        }
        controller.carregar(jogo, quantJogadores);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void addCasaEspecial(int index, Class<? extends Casa> tipoCasa) {
        try {
            validarParametros(index, tipoCasa);
            casasEspeciais.put(index, tipoCasa);
            atualizarListaDeCasasEspeciais();
        } catch (RuntimeException e) {
            ExceptionModal.popUp(e.getMessage());
        }
    }

    private void deleteCasaEspecial(int index) {
        casasEspeciais.remove(index);
        atualizarListaDeCasasEspeciais();
    }


    public void voltar() {}

    private void validarParametros(int index, Class<? extends Casa> tipoCasa) {
        if(index <= 0) {
            throw new IllegalArgumentException("Insira pelo menos 1 casa.");
        }
        if (index > quantCasasSpinner.getValue()) {
            throw new IllegalArgumentException("Insira um valor entre 1 e " + quantCasasSpinner.getValue());
        }
        if(tipoCasa == null) {
            throw new IllegalArgumentException("Escolha uma ação especial.");
        }
    }

    private void atualizarListaDeCasasEspeciais() {
        casasEspeciaisVBox.getChildren().clear();
        for (int i = 1; i <= quantCasasSpinner.getValue(); i++) {
            String descricaoCasa = CasasEspeciaisDescricao.getDescricao(casasEspeciais.get(i));
            if(descricaoCasa != null) {
                casasEspeciaisVBox.getChildren().add(linhaEstilizada(i, descricaoCasa));
            }
        }
    }

    private HBox linhaEstilizada(int index, String descricaoCasa) {
        HBox casaEspecial = new HBox();
        Label label1 = new Label("Casa " + index + ": ");
        Label label2 = new Label(descricaoCasa);
        casaEspecial.setSpacing(20);
        HBox.getHgrow(label2);
        casaEspecial.setAlignment(Pos.CENTER_LEFT);
        label1.setTextFill(Paint.valueOf("white"));
        label2.setTextFill(Paint.valueOf("white"));
        casaEspecial.getChildren().add(label1);
        casaEspecial.getChildren().add(label2);
        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(_ -> {
            deleteCasaEspecial(index);
        });
        casaEspecial.getChildren().add(deleteButton);
        return casaEspecial;
    }
}
