package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;

public class CasaAndarTres extends Casa {

    public CasaAndarTres(int index, ArrayList<Jogador> jogadores) {
        super(index, jogadores);
    }

    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            if (jogador != null && !(jogador instanceof JogadorAzarado)) {
                jogador.andar(3);
            }
        }
    }
}
