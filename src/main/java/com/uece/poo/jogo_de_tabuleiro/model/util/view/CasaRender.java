package com.uece.poo.jogo_de_tabuleiro.model.util.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class CasaRender {

    private CasaRender() {}

    private static String textFill = "white";
    private static String casaStyle = "margin: 10px; padding: 10px";
    private static String childrenPanePrefix = "casaFlowPane";

    public static Pane renderCasa(String tipo, int i) {
        return renderCasaTemplate(tipo, i);
    }

    public static Pane renderCasaInicio(int i) {
        return renderCasaTemplate("INICIO", i);
    }

    public static Pane renderCasaChegada(int i) {
        return renderCasaTemplate("CHEGADA", i);
    }

    private static Pane renderCasaTemplate(String tipo, int i) {
        Pane casa = new Pane();
        casa.setMinHeight(70);
        casa.setMinWidth(100);
        casa.setStyle(casaStyle);
        casa.setBackground(Background.fill(Paint.valueOf(tipo.equals("CHEGADA") ? "#ffaf05" : tipo.equals("INICIO") ? "#00e4e0" : "#1145ab")));
        casa.setBorder(new Border(new BorderStroke(tipo.equals("ESPECIAL") ? Color.YELLOW : Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(tipo.equals("ESPECIAL") ? 3 : 1))));
        VBox casaVBox = new VBox();
        casaVBox.setSpacing(3);
        Label casaIndex = new Label(tipo.equals("ESPECIAL") || tipo.equals("SIMPLES") ? "Casa " + i : tipo);
        casaIndex.setTextFill(Paint.valueOf(textFill));
        casaIndex.setPadding(new Insets(0, 0, 0, 10));
        FlowPane children = new FlowPane();
        children.setVgap(10);
        children.setHgap(10);
        children.setPadding(new Insets(5));
        children.setMaxWidth(100);
        children.setMinHeight(50);
        children.setAlignment(Pos.CENTER);
        children.setId(childrenPanePrefix + i);
        casaVBox.getChildren().add(casaIndex);
        casaVBox.getChildren().add(children);
        casa.setId("casa" + i);
        casa.getChildren().add(casaVBox);
        return casa;
    }

}
