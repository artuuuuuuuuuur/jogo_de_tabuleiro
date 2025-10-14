package com.uece.poo.jogo_de_tabuleiro.model;

public class CasaTrocar extends Casa {

    public CasaTrocar() {
        super();
    }

    public void executarAcaoEspecial(Jogador jogadorAlvo, Tabuleiro tabuleiro) {
        for (Jogador jogadorUser : jogadores) {
            for (Casa casa : tabuleiro.getCasas()) {
                for (Jogador jogador : casa.getJogadores()) {
                    if (jogadorAlvo.equals(jogador)) {
                        Jogador jogadorSwitch = jogador;
                        jogador.setPosicao(jogadorUser.getPosicao());
                        jogadorUser.setPosicao(jogadorSwitch.getPosicao());
                    }
                }
            }
        }
    }
}
