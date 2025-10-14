package com.uece.poo.jogo_de_tabuleiro.model;

public class CasaTrocar extends Casa {

    public CasaTrocar() {
        super();
    }

    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        for (Jogador jogadorUser : jogadores) {
            for (Casa casa : tabuleiro.getCasas()) {
                if (!casa.getJogadores().isEmpty()) {
                    for (Jogador jogador : casa.getJogadores()) {
                        if (!jogador.equals(jogadorUser)) {
                            if (jogadorUser.getJogadorAlvo().equals(jogador)) {
                                Jogador jogadorSwitch = jogador;
                                jogador.setPosicao(jogadorUser.getPosicao());
                                jogadorUser.setPosicao(jogadorSwitch.getPosicao());
                            }
                        }
                    }
                }
                break;
            }
        }
    }
}
