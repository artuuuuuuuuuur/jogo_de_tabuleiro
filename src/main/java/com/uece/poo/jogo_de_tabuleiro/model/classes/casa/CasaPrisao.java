package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaPrisao extends Casa {

    public CasaPrisao(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
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
