package com.uece.poo.jogo_de_tabuleiro.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

import com.uece.poo.jogo_de_tabuleiro.config.Config;
import com.uece.poo.jogo_de_tabuleiro.model.classes.Jogo;
import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaSimples;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

import com.uece.poo.jogo_de_tabuleiro.model.util.CasaListener;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.CasaRender;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.ExceptionModal;
import com.uece.poo.jogo_de_tabuleiro.model.util.JogoListener;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TabuleiroController implements JogoListener, CasaListener {

    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private final Semaphore pauseSemaphore = new Semaphore(0);
    private Jogador jogadorVencedor;
    private final Map<Jogador, Circle> jogadoresIcons = new HashMap<>();
    private Jogo jogo;

    @FXML private Label currentPlayer;
    @FXML private Button jogarDadosButton;
    @FXML private GridPane tabuleiroGrid;
    @FXML private AnchorPane gameAnchorPane;
    @FXML private VBox eventLogVBox;

    public void carregarTabuleiro(Jogo jogo) {
        this.jogo = jogo;
        this.tabuleiro = jogo.getTabuleiro();
        this.jogadores = jogo.getJogadores();

        Logger.bind(this::logNaTela);

        for (Jogador jogador : this.jogadores) {
            Circle jogadorCircle = new Circle(10);
            jogadorCircle.setFill(Paint.valueOf(jogador.getCor()));
            jogadoresIcons.put(jogador, jogadorCircle);
        }

        inicializar();
    }

    private void atualizarStats() {
        Platform.runLater(() -> {
            AnchorPane playersAnchorPane = (AnchorPane) gameAnchorPane.lookup("#playersAnchorPane");
            int i = 1;
            for (Jogador jogadorAtual : jogadores) {
                HBox jogadorAtualHBox = (HBox) playersAnchorPane.lookup("#playerInfo" + i);
                jogadorAtualHBox.setDisable(false);
                jogadorAtualHBox.setBackground(Background.fill(Paint.valueOf(jogadorAtual.getCor())));
                Label jogadorAtualLabel = (Label) jogadorAtualHBox.lookup("#jogadorInfo" + i);
                jogadorAtualLabel.setText(infoJogador(i - 1));
                i++;
            }
            atualizarCasas();
        });
    }

    private String infoJogador(int idx) {
        Jogador j = jogadores.get(idx);
        return j.getNome() + " | Posição: " + j.getPosicao() + " | Moedas: " + j.getMoedas();
    }

    private void atualizarCasas() {
        tabuleiro.atualizarJogadores(jogadores);
        renderizarCasas();
    }

    private void renderizarCasas() {
        for (Casa casa : tabuleiro.getCasas()) {
            int i = casa.getIndex();
            renderizarCasa(casa, i);
        }
    }

    private void renderizarCasa(Casa casa, int i) {
        Pane casaPane;
        if(i == 0) {
            casaPane = CasaRender.renderCasaInicio(i);
        } else if (i == tabuleiro.getCasas().size()-1) {
            casaPane = CasaRender.renderCasaChegada(i);
        } else {
            casaPane = CasaRender.renderCasa((casa instanceof CasaSimples) ? "SIMPLES" : "ESPECIAL", i);
        }
        renderizarJogadores(i, casaPane);
        if ((i / 6) % 2 == 0) {
            tabuleiroGrid.add(casaPane, i / 6, (i % 6));
        } else {
            tabuleiroGrid.add(casaPane, i / 6, (5 - (i % 6)));
        }
    }

    private void renderizarJogadores(int i, Pane casaPane) {
        for (Jogador jogador : jogadores) {
            if (jogador.getPosicao() == i) {
                FlowPane casaFlowPane = (FlowPane) casaPane.lookup("#casaFlowPane"+ i);
                Circle circle = jogadoresIcons.get(jogador);
                if (!casaFlowPane.getChildren().contains(circle)) {
                    casaFlowPane.getChildren().add(circle);
                }
            }
        }
    }

    private void mostrarJogadorAtual(Jogador jogador) {
        Platform.runLater(() -> {
            currentPlayer.setText(jogador.getNome() + " jogou.");
            atualizarCasas();
        });
    }

    @FXML public void resume() {
        pauseSemaphore.release();
    }

    private void pause() {
        try {
            pauseSemaphore.acquire();
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }
    }

    private void debugModePage(CompletableFuture<Integer> resultadoDebug) {
        Platform.runLater(() -> {
            Stage popup = new Stage();
            popup.setTitle("Modo Debug - Valor dos Dados");

            Label label = new Label("Digite o valor que deseja andar: ");
            TextField input = new TextField();
            input.setPromptText("ex: 7");

            Button confirmar = new Button("Confirmar");

            confirmar.setOnAction(e -> {
                try {
                    int valor = Integer.parseInt(input.getText());
                    resultadoDebug.complete(valor);
                    popup.close();
                } catch (NumberFormatException _) {
                    input.setStyle("-fx-border-color: red;");
                }
            });

            VBox layout = new VBox(10, label, input, confirmar);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            popup.setScene(new Scene(layout, 300, 150));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.show();
        });
    }

    private void checkWinner(Jogador jogador) {
        Platform.runLater(this::atualizarStats);
        Platform.runLater(() -> {
            if (jogador.getPosicao() >= jogo.getTabuleiro().getCasas().size()-1) {
                jogador.setPosicao(jogo.getTabuleiro().getCasas().size()-1);
                jogadorVencedor = jogador;
                Platform.runLater(() -> {
                    currentPlayer.setText("🏆 " + jogadorVencedor.getNome() + " venceu!");
                    jogarDadosButton.setDisable(true);
                    atualizarStats();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        try {
                            switchToRank();
                        } catch (IOException _) {
                            ExceptionModal.popUp("Não foi possível abrir página do rank.");
                        }
                    });
                    pause.play();
                });
            }
        });
    }

    private void rollDicesPage(Jogador jogador) {
        Stage popup = new Stage();
        Label plusSign = new Label("+");
        plusSign.setFont(Font.font(30));
        Label equalsSign = new Label("=");
        equalsSign.setFont(Font.font(30));
        Label total = new Label(String.valueOf(jogador.getDados()[0] + jogador.getDados()[1]));
        total.setFont(Font.font(30));
        popup.setTitle("Modo Normal - Valor dos Dados");

        HBox layout = new HBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 800, 300);
        popup.setScene(scene);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.show();

        Timeline timeline = new Timeline();
        int frame = 0;

        String dicesImagePath = Config.get("dicesImagePath");

        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 6; j++) {
                timeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(200 * frame++),
                        e -> {
                            layout.getChildren().clear();
                            Image img1 = new Image(getClass().getResourceAsStream(
                                    dicesImagePath + ((new Random().nextInt(6)) + 1) + ".png"
                            ), 200, 200, false, false);
                            Image img2 = new Image(getClass().getResourceAsStream(
                                    dicesImagePath + ((new Random().nextInt(6)) + 1) + ".png"
                            ), 200, 200, false, false);

                            layout.getChildren().add(new ImageView(img1));
                            layout.getChildren().add(plusSign);
                            layout.getChildren().add(new ImageView(img2));

                        }
                ));
            }
        }

        timeline.setOnFinished(e -> {
            layout.getChildren().clear();
            Image dado1 = new Image(getClass().getResourceAsStream(
                    dicesImagePath + jogador.getDados()[0] + ".png"
            ), 200, 200, false, false);
            Image dado2 = new Image(getClass().getResourceAsStream(
                    dicesImagePath + jogador.getDados()[1] + ".png"
            ), 200, 200, false, false);
            layout.getChildren().add(new ImageView(dado1));
            layout.getChildren().add(plusSign);
            layout.getChildren().add(new ImageView(dado2));
            layout.getChildren().add(equalsSign);
            layout.getChildren().add(total);
            // Espera 2 segundos antes de fechar (sem travar a interface)
            PauseTransition wait = new PauseTransition(Duration.seconds(2));
            wait.setOnFinished(ev -> {
                popup.close();
                atualizarStats();
            });
            wait.play();
        });
        timeline.play();
    }

    private void atualizarCasasSync() {
        for (Casa casa : tabuleiro.getCasas()) {
            casa.clearJogadores();
        }

        for (Jogador jogador : jogadores) {
            Casa casa = tabuleiro.getCasa(jogador.getPosicao());
            if (casa != null) {
                casa.addJogador(jogador);
            }
        }
    }

    private void switchToRank() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uece/poo/jogo_de_tabuleiro/final_rank.fxml"));
        Parent root = loader.load();
        FinalRankController finalRankController = loader.getController();
        finalRankController.carregar(jogo);
        Stage stage = (Stage) gameAnchorPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void inicializar() {
        atualizarStats();
        jogo.start();
    }

    private void logNaTela(String efeito) {
        Platform.runLater(() -> {
            Label label = new Label(efeito);
            label.setTextFill(Paint.valueOf("white"));
            label.setFont(Font.font(14));
            eventLogVBox.getChildren().add(label);
        });
    }

    @Override
    public void onTurnoIniciado(Jogador jogador) {
        Platform.runLater(() -> {
            currentPlayer.setText("Vez de " + jogador.getNome());
            jogarDadosButton.setDisable(false);
        });
        pause();
    }

    @Override
    public void onDepoisDeJogarDados(Jogador jogador, CompletableFuture<Integer> resultadoDebug) {
        mostrarJogadorAtual(jogador);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException _) {
            ExceptionModal.popUp("Erro ao pausar thread.");
        }
        Platform.runLater(() -> atualizarCasas());
        atualizarCasasSync();
    }

    @Override
    public void onNormalMode(Jogador jogador) {
        Platform.runLater(() -> {
            rollDicesPage(jogador);
            });
        try {
            Thread.sleep(4000);
        } catch (InterruptedException _){
            ExceptionModal.popUp("Erro ao pausar thread.");
        }
    }

    @Override
    public void onDebugMode(Jogador jogador, CompletableFuture<Integer> resultadoDebug) {
        debugModePage(resultadoDebug);

        Platform.runLater(this::atualizarCasas);
        Platform.runLater(this::atualizarStats);
        atualizarCasasSync();
    }

    @Override
    public void onMovimentoConcluido(Jogador jogador) {
        Platform.runLater(() -> {
            atualizarCasas();
            atualizarCasasSync();
            atualizarStats();
        });
    }

    @Override
    public void onCasaAplicada(String efeito) {
        Platform.runLater(() -> {
            logNaTela(efeito);
            atualizarCasasSync();
            atualizarCasas();
        });
    }

    @Override
    public void onVitoria(Jogador jogador) {
        Platform.runLater(() -> checkWinner(jogador));
        Platform.runLater(() -> atualizarCasas());
    }
}
