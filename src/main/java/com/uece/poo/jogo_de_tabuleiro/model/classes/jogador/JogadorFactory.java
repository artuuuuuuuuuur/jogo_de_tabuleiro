package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

import java.lang.reflect.InvocationTargetException;

import com.uece.poo.jogo_de_tabuleiro.model.util.ExceptionModal;

public class JogadorFactory {

    public static Jogador getJogador(Class<? extends Jogador> jogadorType, String cor, String nome) {
        try {
            return jogadorType.getConstructor(String.class, String.class).newInstance(cor, nome);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            ExceptionModal.popUp(e.getMessage());
            return null;
        }
    }
    
    public static Jogador getJogador(Class<? extends Jogador> jogadorType, boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais)  {
        Jogador newJogador = getJogador(jogadorType, cor, nome);
        newJogador.setAtivo(ativo);
        newJogador.setJogarNovamente(jogarNovamente);
        newJogador.setPosicao(posicao);
        newJogador.setVezesJogadas(vezesJogadas);
        newJogador.setDadosIguais(dadosIguais);
        return newJogador;
    }
}
