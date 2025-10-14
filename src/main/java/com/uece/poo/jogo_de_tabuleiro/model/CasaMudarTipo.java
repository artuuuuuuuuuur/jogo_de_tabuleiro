package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;
import java.util.Random;

public class CasaMudarTipo extends Casa {

    public CasaMudarTipo(int index, ArrayList<Jogador> jogadores) {
        super(index, jogadores);
    }

    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            Random random = new Random();
            int tipo = random.nextInt(3);
            switch (tipo) {
                case 0 -> {
                    jogadores.add(new JogadorNormal(jogador.isAtivo(), jogador.getCor(), jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas()));
                    jogadores.remove(jogador);
                }
                case 1 -> {
                    jogadores.add(new JogadorComSorte(jogador.isAtivo(), jogador.getCor(), jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas()));
                    jogadores.remove(jogador);
                }
                case 2 -> {
                    jogadores.add(new JogadorAzarado(jogador.isAtivo(), jogador.getCor(), jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas()));
                    jogadores.remove(jogador);
                }
                default -> throw new AssertionError();
            }
        }

    }
}
