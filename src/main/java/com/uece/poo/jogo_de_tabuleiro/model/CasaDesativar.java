package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;

public class CasaDesativar extends Casa {

    public CasaDesativar(int index, ArrayList<Jogador> jogadores) {
        super(index, jogadores);
    }

    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            jogador.setAtivo(false);
        }
    }
}
