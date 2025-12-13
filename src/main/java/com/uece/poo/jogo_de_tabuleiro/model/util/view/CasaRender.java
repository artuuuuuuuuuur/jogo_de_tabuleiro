package com.uece.poo.jogo_de_tabuleiro.model.util.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class CasaRender {
    public static Pane renderCasa(boolean isEspecial, int i) {
        Pane casa = new Pane();
        casa.setMinHeight(70);
        casa.setMinWidth(100);
        casa.setStyle("margin: 10px; padding: 10px");
        casa.setBackground(Background.fill(Paint.valueOf("#1145ab")));
        casa.setBorder(Border.stroke(isEspecial ? Paint.valueOf("yellow") : Paint.valueOf("black")));
        FlowPane children = new FlowPane();
        children.setVgap(10);
        children.setHgap(10);
        children.setId("casaFlowPane" + i);
        casa.setId("casa" + i);
        casa.getChildren().add(children);
        return casa;
    }

}
