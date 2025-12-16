package com.uece.poo.jogo_de_tabuleiro.model.util.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class CasaRender {


    private CasaRender() {}

    private static final String TEXT_FILL = "white";
    private static final String CASA_STYLE = "margin: 10px; padding: 10px";
    private static final String CHILDREN_PANE_PREFIX = "casaFlowPane";
    private static final String INICIO = "INICIO";
    private static final String CHEGADA = "CHEGADA";
    private static final String ESPECIAL = "ESPECIAL";
    private static final String SIMPLES = "SIMPLES";

    public static Pane renderCasa(String tipo, int i) {
        return renderCasaTemplate(tipo, i);
    }

    public static Pane renderCasaInicio(int i) {
        return renderCasaTemplate(INICIO, i);
    }

    public static Pane renderCasaChegada(int i) {
        return renderCasaTemplate(CHEGADA, i);
    }

    private static Pane renderCasaTemplate(String tipo, int i) {
        Pane casa = new Pane();
        casa.setMinHeight(70);
        casa.setMinWidth(100);
        casa.setStyle(CASA_STYLE);
        casa.setBackground(Background.fill(Paint.valueOf(getCor(tipo))));
        casa.setBorder(new Border(new BorderStroke(tipo.equals(ESPECIAL) ? Color.YELLOW : Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(tipo.equals(ESPECIAL) ? 3 : 1))));
        VBox casaVBox = new VBox();
        casaVBox.setSpacing(3);
        Label casaIndex = new Label(tipo.equals(ESPECIAL) || tipo.equals(SIMPLES) ? "Casa " + i : tipo);
        casaIndex.setTextFill(Paint.valueOf(TEXT_FILL));
        casaIndex.setPadding(new Insets(0, 0, 0, 10));
        FlowPane children = new FlowPane();
        children.setVgap(10);
        children.setHgap(10);
        children.setPadding(new Insets(5));
        children.setMaxWidth(100);
        children.setMinHeight(50);
        children.setAlignment(Pos.CENTER);
        children.setId(CHILDREN_PANE_PREFIX + i);
        casaVBox.getChildren().add(casaIndex);
        casaVBox.getChildren().add(children);
        casa.setId("casa" + i);
        casa.getChildren().add(casaVBox);
        return casa;
    }

    private static String getCor(String tipo) {
        switch (tipo) {
            case CHEGADA -> {
                return "#ffaf05";
            }
            case INICIO -> {
                return "#00e4e0";
            }
            default -> {
                return "#1145ab";
            }
        }
    }

}
