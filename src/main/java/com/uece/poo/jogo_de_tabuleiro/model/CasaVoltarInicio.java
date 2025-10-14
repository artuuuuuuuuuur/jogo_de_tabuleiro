package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;

public class CasaVoltarInicio extends Casa {
    public CasaVoltarInicio(int index, ArrayList<Jogador> jogadores) {
        super(index, jogadores);
    }
    
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogadorUser : jogadores) {
            for (Casa casa : tabuleiro.getCasas()) {
                for (Jogador jogador : casa.getJogadores()) {
                    if (jogadorUser.getJogadorAlvo().equals(jogador)) {
                        jogador.setPosicao(0);
                    }
                }
            }
        }
    }
}
