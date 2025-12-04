package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaSimples extends Casa {
    public CasaSimples(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaSimples(int index) {
        super(index);
    }
    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        // Não faz nada
    }
}
