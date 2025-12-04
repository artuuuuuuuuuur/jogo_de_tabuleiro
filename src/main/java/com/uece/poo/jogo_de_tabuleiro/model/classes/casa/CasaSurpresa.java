package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorSortudo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorNormal;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CasaSurpresa extends Casa {

    public CasaSurpresa(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        List<Jogador> copia = new ArrayList<>(this.getJogadores());

        List<Replacement> replacements = new ArrayList<>();
        Random random = new Random();

        for (Jogador jogador : copia) {
            if (jogador.getLastCasaEspecialIndex() == this.getIndex()) {
                continue;
            }

            int tipo = random.nextInt(2);
            Jogador novo;
            if (jogador instanceof JogadorNormal) {
                switch (tipo) {
                    case 0 ->
                        novo = new JogadorAzarado(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                                jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                    case 1 ->
                        novo = new JogadorSortudo(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                                jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                    default ->
                        throw new AssertionError();
                }
            } else if (jogador instanceof JogadorSortudo) {
                switch (tipo) {
                    case 0 ->
                        novo = new JogadorAzarado(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                                jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                    case 1 ->
                        novo = new JogadorNormal(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                                jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                    default ->
                        throw new AssertionError();
                }
            } else {
                switch (tipo) {
                    case 0 ->
                        novo = new JogadorSortudo(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                                jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                    case 1 ->
                        novo = new JogadorNormal(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                                jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                    default ->
                        throw new AssertionError();
                }
            }

            replacements.add(new Replacement(jogador, novo));
        }

        for (Replacement r : replacements) {
            List<Jogador> listaGlobal = tabuleiro.getJogadoresGlobal();

            int idx = listaGlobal.indexOf(r.oldPlayer);
            if (idx >= 0) {
                listaGlobal.set(idx, r.newPlayer);
                r.newPlayer.setLastCasaEspecialIndex(this.getIndex());
                System.out.println(r.oldPlayer.getNome() + " mudou de tipo. Novo tipo: " + r.newPlayer.getClass());
                Platform.runLater(() -> {
                    Stage popup = new Stage();
                    Label mudarTipoLabel = new Label("Escolha uma carta para mudar de tipo:");
                    mudarTipoLabel.setFont(Font.font(30));
                    popup.setTitle("Mudança de Tipo");
                    Button cardButton1 = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/uece/poo/jogo_de_tabuleiro/assets/cartas/carta_default.png"))));
                    Button cardButton2 = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/uece/poo/jogo_de_tabuleiro/assets/cartas/carta_default.png"))));

                    cardButton1.setOnAction(e -> {
                        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                        pause.setOnFinished(ev -> {
                            cardButton1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(
                                    "/com/uece/poo/jogo_de_tabuleiro/assets/cartas/carta_" + r.newPlayer.getTipo().toLowerCase().replaceAll(" ", "") + ".png"
                            ))));

                            PauseTransition showCard = new PauseTransition(Duration.seconds(3));
                            showCard.setOnFinished(ev2 -> popup.close());
                            showCard.play();
                        });
                        pause.play();
                    });
                    cardButton2.setOnAction(e -> {
                        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                        pause.setOnFinished(ev -> {
                            cardButton2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(
                                    "/com/uece/poo/jogo_de_tabuleiro/assets/cartas/carta_" + r.newPlayer.getTipo().toLowerCase().replaceAll(" ", "") + ".png"
                            ))));

                            PauseTransition showCard = new PauseTransition(Duration.seconds(3));
                            showCard.setOnFinished(ev2 -> popup.close());
                            showCard.play();
                        });
                        pause.play();
                    });

                    VBox layout = new VBox(30);
                    HBox cartasBox = new HBox(40);
                    layout.setAlignment(Pos.CENTER);
                    layout.setPadding(new Insets(20));
                    cartasBox.getChildren().add(cardButton1);
                    cartasBox.getChildren().add(cardButton2);
                    layout.getChildren().add(mudarTipoLabel);
                    layout.getChildren().add(cartasBox);
                    Scene scene = new Scene(layout, 800, 700);
                    popup.setScene(scene);
                    popup.initModality(Modality.APPLICATION_MODAL);
                    popup.show();
                });
            }
        }
    }

    private static class Replacement {

        final Jogador oldPlayer;
        final Jogador newPlayer;

        Replacement(Jogador o, Jogador n) {
            oldPlayer = o;
            newPlayer = n;
        }
    }
}
