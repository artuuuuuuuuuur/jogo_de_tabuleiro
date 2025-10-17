package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.List;

public class CasaDesativar extends Casa {

    public CasaDesativar(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
            for (Jogador jogador : jogadores) {
                if (jogador.isAtivo()) {
                    jogador.setAtivo(false);
                    System.out.println(jogador.getNome() + " está inativo por 1 rodada.");
                } else {
                    jogador.jogar();
                }
            }
    }
}
