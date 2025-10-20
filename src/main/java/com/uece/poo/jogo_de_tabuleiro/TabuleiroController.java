package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import com.uece.poo.jogo_de_tabuleiro.model.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TabuleiroController {

    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private volatile boolean partidaTerminada = false;
    private final Semaphore pauseSemaphore = new Semaphore(0);
    private Jogador jogadorVencedor;
    private final Map<Jogador, Circle> jogadoresIcons = new HashMap<>();
    private boolean debugMode;

    @FXML
    private Label currentPlayer;
    @FXML
    private Button jogarDadosButton;
    @FXML
    private GridPane tabuleiroPane;
    @FXML
    AnchorPane gameAnchorPane;

    public void carregarTabuleiro(Tabuleiro tabuleiro, List<Jogador> jogadores, boolean debugMode) {
        this.tabuleiro = tabuleiro;
        this.jogadores = new CopyOnWriteArrayList<>(jogadores);

        this.debugMode = debugMode;

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
        return j.getNome() + " | Posição: " + j.getPosicao();
    }

    private void atualizarCasas() {
        for (Casa casa : tabuleiro.getCasas()) {
            casa.clearJogadores();
        }

        for (Jogador jogador : jogadores) {
            Casa casa = tabuleiro.getCasa(jogador.getPosicao());
            if (casa != null) {
                casa.addJogador(jogador);
            }
        }

        for (Jogador jogador : jogadores) {
            Pane casaAtual = (Pane) tabuleiroPane.lookup("#casa" + jogador.getPosicao());
            FlowPane casaAtualFlowPane = (FlowPane) casaAtual.lookup("#casaFlowPane" + jogador.getPosicao());
            if (casaAtualFlowPane != null) {
                Circle circle = jogadoresIcons.get(jogador);
                if (!casaAtualFlowPane.getChildren().contains(circle)) {
                    casaAtualFlowPane.getChildren().add(circle);
                }
            }
        }
    }

    private void jogarPartida() {
        new Thread(() -> {
            while (!partidaTerminada) {
                for (Jogador jogador : jogadores) {
                    if (partidaTerminada) {
                        break;
                    }

                    if (jogador.isAtivo()) {
                        final Jogador current = jogador;
                        Platform.runLater(() -> {
                            currentPlayer.setText("Vez de " + current.getNome());
                            jogarDadosButton.setDisable(false);
                        });

                        pause();

                        if (debugMode) {
                            CompletableFuture<Integer> resultadoDebug = new CompletableFuture<>();

                            debugModePage(resultadoDebug);

                            Platform.runLater(() -> atualizarCasas());

                            int tempValorDados;
                            try {
                                tempValorDados = resultadoDebug.get();
                            } catch (Exception e) {
                                e.printStackTrace();
                                tempValorDados = 7;
                            }
                            final int valorDados = tempValorDados;

                            jogador.setPosicao(jogador.getPosicao() + valorDados);

                            Platform.runLater(() -> {
                                atualizarStats();
                            });

                            atualizarCasasSync();

                            Casa casaAtual = tabuleiro.getCasa(jogador.getPosicao());
                            if (casaAtual != null) {
                                casaAtual.executarAcaoEspecial(tabuleiro);
                            }

                            atualizarCasasSync();
                            Platform.runLater(this::atualizarStats);

                        } else {
                            jogador.jogar();

                            Platform.runLater(() -> {
                                rollDicesPage(jogador);
                            });

                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Platform.runLater(() -> atualizarCasas());
                            atualizarCasasSync();

                            Casa casaAtual = tabuleiro.getCasa(jogador.getPosicao());
                            if (casaAtual != null) {
                                casaAtual.executarAcaoEspecial(tabuleiro);
                                atualizarCasasSync();
                                Platform.runLater(() -> atualizarCasas());
                            }

                            Platform.runLater(this::atualizarStats);
                        }

                        Platform.runLater(() -> checkWinner(jogador));
                        Platform.runLater(() -> atualizarCasas());

                        if (jogador.isDadosIguais() && !debugMode) {
                            Platform.runLater(() -> {
                                currentPlayer.setText(jogador.getNome() + " tirou dados iguais! Joga novamente!");
                                jogarDadosButton.setDisable(false);
                            });

                            pause();

                            jogador.jogar();
                            Platform.runLater(() -> {
                                rollDicesPage(jogador);
                            });

                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                            }
                            Platform.runLater(this::atualizarStats);
                            atualizarCasasSync();

                            Casa casaAtual = tabuleiro.getCasa(jogador.getPosicao());
                            if (casaAtual != null) {
                                casaAtual.executarAcaoEspecial(tabuleiro);
                            }

                            Platform.runLater(this::atualizarStats);

                            Platform.runLater(() -> checkWinner(jogador));
                            Platform.runLater(() -> atualizarCasas());
                        }
                    } else {
                        jogador.setAtivo(true);
                        System.out.println(jogador.getNome() + " agora pode jogar.");
                    }
                }
            }
        }, "Thread-Jogo").start();
    }

    public void resume() {
        pauseSemaphore.release();
    }

    private void pause() {
        try {
            pauseSemaphore.acquire();
        } catch (InterruptedException e) {
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
                } catch (NumberFormatException ex) {
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
            if (jogador.getPosicao() >= 40) {
                jogador.setPosicao(40);
                partidaTerminada = true;
                jogadorVencedor = jogador;
                Platform.runLater(() -> {
                    currentPlayer.setText("🏆 " + jogadorVencedor.getNome() + " venceu!");
                    jogarDadosButton.setDisable(true);
                    atualizarStats();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        try {
                            switchToRank();
                        } catch (IOException ex) {
                            ex.printStackTrace();
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

        // 3 giros do dado (3 * 6 frames)
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 6; j++) {
                timeline.getKeyFrames().add(new KeyFrame(
                        Duration.millis(200 * frame++),
                        e -> {
                            layout.getChildren().clear();
                            Image img1 = new Image(getClass().getResourceAsStream(
                                    "/com/uece/poo/jogo_de_tabuleiro/assets/dados/" + ((new Random().nextInt(6)) + 1) + ".png"
                            ), 200, 200, false, false);
                            Image img2 = new Image(getClass().getResourceAsStream(
                                    "/com/uece/poo/jogo_de_tabuleiro/assets/dados/" + ((new Random().nextInt(6)) + 1) + ".png"
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
                    "/com/uece/poo/jogo_de_tabuleiro/assets/dados/" + jogador.getDados()[0] + ".png"
            ), 200, 200, false, false);
            Image dado2 = new Image(getClass().getResourceAsStream(
                    "/com/uece/poo/jogo_de_tabuleiro/assets/dados/" + jogador.getDados()[1] + ".png"
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
        finalRankController.carregar(jogadores, debugMode);
        Stage stage = (Stage) gameAnchorPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void inicializar() {
        atualizarStats();
        jogarPartida();
    }
}
