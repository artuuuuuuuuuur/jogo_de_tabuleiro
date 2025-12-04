package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorFactory;
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
    List<Class<?extends Jogador>> possiveisClasses;
    

    public CasaSurpresa(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaSurpresa(int index) {
        super(index);
    }
    
    private void setPossiveisClasses() {
        possiveisClasses = new ArrayList<>();
        possiveisClasses.add(JogadorAzarado.class);
        possiveisClasses.add(JogadorSortudo.class);
        possiveisClasses.add(JogadorNormal.class);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        List<Jogador> copia = new ArrayList<>(this.getJogadores());

        List<Replacement> replacements = new ArrayList<>();

        for (Jogador jogador : copia) {
            if (jogador.getLastCasaEspecialIndex() == this.getIndex()) {
                continue;
            }

            Jogador novoTipo = predefinirJogador(jogador);
            replacements.add(new Replacement(jogador, novoTipo));
        }
        executarTrocasDeTipo(replacements, tabuleiro);
    }

    private Jogador predefinirJogador(Jogador jogador) {
        Random random = new Random();
        int tipo = random.nextInt(2);
        setPossiveisClasses();

        possiveisClasses.remove(jogador.getClass());

        return (tipo == 0)
                ? JogadorFactory.getJogador(possiveisClasses.get(0), jogador)
                : JogadorFactory.getJogador(possiveisClasses.get(1), jogador);
    }

    private void executarTrocasDeTipo(List<Replacement> replacements, Tabuleiro tabuleiro) {
        for (Replacement r : replacements) {
            List<Jogador> listaGlobal = tabuleiro.getJogadoresGlobal();

            int idx = listaGlobal.indexOf(r.oldPlayer);
            if (idx >= 0) {
                listaGlobal.set(idx, r.newPlayer);
                r.newPlayer.setLastCasaEspecialIndex(this.getIndex());
                System.out.println(r.oldPlayer.getNome() + " mudou de tipo. Novo tipo: " + r.newPlayer.getClass());
                abrirPopUpDeEscolha(r);
            }
        }
    }

    private void abrirPopUpDeEscolha(Replacement r) {
        String cartasPath = "/com/uece/poo/jogo_de_tabuleiro/assets/cartas/carta_";
        Platform.runLater(() -> {
            Stage popup = new Stage();
            Label mudarTipoLabel = new Label("Escolha uma carta para mudar de tipo:");
            mudarTipoLabel.setFont(Font.font(30));
            popup.setTitle("Mudança de Tipo");
            List<Button> cardButtons = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Button cardButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream(cartasPath + "default.png"))));

                cardButton.setOnAction(e -> {
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(ev -> {
                        cardButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(
                                cartasPath + r.newPlayer.getTipo().toLowerCase().replaceAll(" ", "") + ".png"
                        ))));

                        PauseTransition showCard = new PauseTransition(Duration.seconds(3));
                        showCard.setOnFinished(ev2 -> popup.close());
                        showCard.play();
                    });
                    pause.play();
                });
                cardButtons.add(cardButton);
            }

            VBox layout = new VBox(30);
            HBox cartasBox = new HBox(40);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));
            cartasBox.getChildren().add(cardButtons.get(0));
            cartasBox.getChildren().add(cardButtons.get(1));
            layout.getChildren().add(mudarTipoLabel);
            layout.getChildren().add(cartasBox);
            Scene scene = new Scene(layout, 800, 700);
            popup.setScene(scene);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.show();
        });
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
