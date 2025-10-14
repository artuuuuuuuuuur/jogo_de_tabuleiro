package com.uece.poo.jogo_de_tabuleiro.model;

public class CasaDesativar extends Casa {

    public CasaDesativar() {
        super();
    }

    public void executarAcaoEspecial(Jogador jogadorAlvo, Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            jogador.setAtivo(false);
        }
    }
}
