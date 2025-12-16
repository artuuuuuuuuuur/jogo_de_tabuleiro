package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class CasaSimples extends Casa {
    public CasaSimples(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaSimples(int index) {
        super(index);
    }
    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        jogador.setMoedas(jogador.getMoedas() + index);
        Logger.log(jogador.getNome() + " ganhou " + index + " moedas.");
    }

}
