package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class CasaJogaDeNovo extends Casa{

    public CasaJogaDeNovo(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaJogaDeNovo(int index) {
        super(index);
    }
    
    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        jogador.setJogarNovamente(true);
        Logger.log((jogador.getNome() + " irá jogar novamente."));
    }
}
