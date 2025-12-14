package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class CasaPrisao extends Casa {

    public CasaPrisao(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaPrisao(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        if (jogador.isAtivo()) {
            jogador.setAtivo(false);
            Logger.log((jogador.getNome() + " está inativo por 1 rodada."));
        } else {
            jogador.jogar();
        }
    }
}
