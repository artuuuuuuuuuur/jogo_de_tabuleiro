package com.uece.poo.jogo_de_tabuleiro.model.util;

import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

import java.util.concurrent.CompletableFuture;

public interface JogoListener {
        void onTurnoIniciado(Jogador jogador);
        void onDepoisDeJogarDados(Jogador jogador, CompletableFuture<Integer> resultadoDebug);
        void onNormalMode(Jogador jogador);
        void onDebugMode(Jogador jogador, CompletableFuture<Integer> resultadoDebug);
        void onMovimentoConcluido(Jogador jogador);
        void onVitoria(Jogador jogador);
}
