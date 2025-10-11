package com.uece.poo.jogo_de_tabuleiro.model;

public class CasaDesativar extends Casa {
    public CasaDesativar() {
        super();
    }
    
    public void executarAcaoEspecial() {
        for (Jogador jogador : jogadores) {
            jogador.setAtivo(false);
        }
    }
}
