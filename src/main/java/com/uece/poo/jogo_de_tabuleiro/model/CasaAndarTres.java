package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.List;


public class CasaAndarTres extends Casa {

    public CasaAndarTres(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            if (jogador != null && !(jogador instanceof JogadorAzarado)) {
                if(jogador.getPosicao() == index) {
                    jogador.andar(3);
                    System.out.println(jogador.getNome() + " andou 3 casas.");
                }
            }
        }
    }

}
