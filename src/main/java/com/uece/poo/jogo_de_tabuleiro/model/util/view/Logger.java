package com.uece.poo.jogo_de_tabuleiro.model.util.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.function.Consumer;

public final class Logger {
    private static Consumer<String> logConsumer;
    private Logger() {}

    public static void bind(Consumer<String> consumer) {
        logConsumer = consumer;
    }

    public static void log(String mensagem) {
        if (logConsumer == null) return;

        if (Platform.isFxApplicationThread()) {
            logConsumer.accept(mensagem);
        } else {
            Platform.runLater(() -> logConsumer.accept(mensagem));
        }
    }
}
