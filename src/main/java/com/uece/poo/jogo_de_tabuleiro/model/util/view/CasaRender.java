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

    public static Pane renderCasa(boolean isEspecial, int i) {
        Pane casa = new Pane();
        casa.setBorder(new Border(new BorderStroke(isEspecial ? Color.YELLOW : Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        casa.setMinHeight(70);
        casa.setMinWidth(100);
        casa.setStyle(casaStyle);
        casa.setBackground(Background.fill(Paint.valueOf("#1145ab")));
        VBox casaVBox = new VBox();
        casaVBox.setSpacing(3);
        Label casaIndex = new Label("Casa " + i);
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

    public static Pane renderCasaInicio() {
        Pane casa = new Pane();
        casa.setMinHeight(70);
        casa.setMinWidth(100);
        casa.setStyle(casaStyle);
        casa.setBackground(Background.fill(Paint.valueOf("#00e4e0")));
        casa.setBorder(Border.stroke(Paint.valueOf("black")));
        VBox casaVBox = new VBox();
        casaVBox.setSpacing(3);
        Label casaIndex = new Label("Início");
        casaIndex.setTextFill(Paint.valueOf(textFill));
        casaIndex.setPadding(new Insets(0, 0, 0, 10));
        FlowPane children = new FlowPane();
        children.setVgap(10);
        children.setHgap(10);
        children.setPadding(new Insets(5));
        children.setMaxWidth(100);
        children.setMinHeight(50);
        children.setAlignment(Pos.CENTER);
        children.setId(childrenPanePrefix + 0);
        casaVBox.getChildren().add(casaIndex);
        casaVBox.getChildren().add(children);
        casa.setId("casa" + 0);
        casa.getChildren().add(casaVBox);
        return casa;
    }

    public static Pane renderCasaChegada(int i) {
        Pane casa = new Pane();
        casa.setMinHeight(70);
        casa.setMinWidth(100);
        casa.setStyle(casaStyle);
        casa.setBackground(Background.fill(Paint.valueOf("#ffaf05")));
        casa.setBorder(Border.stroke(Paint.valueOf("black")));
        VBox casaVBox = new VBox();
        casaVBox.setSpacing(3);
        Label casaIndex = new Label("Chegada");
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
