package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.List;

public class CasaAndarTres extends Casa {

    public CasaAndarTres(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            if (isTarget(jogador)) {
                jogador.andar(3);
                System.out.println(jogador.getNome() + " andou 3 casas.");
            }
        }
    }

    private boolean isTarget(Jogador jogador) {
        return jogador != null
                && !(jogador instanceof JogadorAzarado)
                && jogador.getPosicao() == index;
    }

}
