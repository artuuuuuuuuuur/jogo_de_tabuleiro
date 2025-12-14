package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class CasaVoltarInicio extends Casa {

    public CasaVoltarInicio(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaVoltarInicio(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        jogador.setPosicao(0);
        jogador.setLastCasaEspecialIndex(this.getIndex());
        Logger.log((jogador.getNome() + " voltou para o início."));
    }
}
