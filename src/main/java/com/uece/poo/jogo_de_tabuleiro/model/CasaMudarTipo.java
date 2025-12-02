package com.uece.poo.jogo_de_tabuleiro.model;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

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

public class CasaMudarTipo extends Casa {

    List<Class<? extends Jogador>> possiveisClasses;

    public CasaMudarTipo(int index, List<Jogador> jogadores) {
        super(index, jogadores);
        possiveisClasses = new ArrayList<>();
        possiveisClasses.add(JogadorAzarado.class);
        possiveisClasses.add(JogadorComSorte.class);
        possiveisClasses.add(JogadorNormal.class);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
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

        possiveisClasses.remove(jogador.getClass());

        return (tipo == 0)
                ? criarJogador(possiveisClasses.get(0), jogador)
                : criarJogador(possiveisClasses.get(1), jogador);
    }

    private Jogador criarJogador(Class<? extends Jogador> tipo, Jogador base) {
        try {
            return tipo.getConstructor(
                    boolean.class, String.class, String.class, boolean.class,
                    int.class, int.class, boolean.class
            ).newInstance(
                    base.isAtivo(), base.getCor(), base.getNome(),
                    base.isJogarNovamente(), base.getPosicao(),
                    base.getVezesJogadas(), base.isDadosIguais()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
