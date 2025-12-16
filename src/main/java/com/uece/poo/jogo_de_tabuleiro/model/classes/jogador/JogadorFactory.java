package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

import java.lang.reflect.InvocationTargetException;

import com.uece.poo.jogo_de_tabuleiro.model.util.view.ExceptionModal;

public class JogadorFactory {

    private JogadorFactory() {}

    public static Jogador getJogador(Class<? extends Jogador> jogadorType, String cor, String nome, int quantCasas) {
        try {
            return jogadorType.getConstructor(String.class, String.class, int.class).newInstance(cor, nome, quantCasas);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            ExceptionModal.popUp(e.getMessage());
            return null;
        }
    }
    
    public static Jogador getJogador(Class<? extends Jogador> jogadorType, Jogador jogador)  {
        Jogador newJogador = getJogador(jogadorType, jogador.getCor(), jogador.getNome(), jogador.getQuantCasas());
        newJogador.setAtivo(jogador.isAtivo());
        newJogador.setTentarNovamente(jogador.isTentarNovamente());
        newJogador.setPosicao(jogador.getPosicao());
        newJogador.setVezesJogadas(jogador.getVezesJogadas());
        newJogador.setJogarNovamente(jogador.isJogarNovamente());
        newJogador.setQuantCasas(jogador.getQuantCasas());
        return newJogador;
    }
}
