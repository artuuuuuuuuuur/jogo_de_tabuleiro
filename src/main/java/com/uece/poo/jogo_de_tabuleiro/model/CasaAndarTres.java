package com.uece.poo.jogo_de_tabuleiro.model;

public class CasaAndarTres extends Casa {

    public CasaAndarTres() {
        super();
    }

    public void executarAcaoEspecial(Jogador jogadorAlvo, Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            if (jogador != null && !(jogador instanceof JogadorAzarado)) {
                jogador.andar(3);
            }
        }
    }
}
