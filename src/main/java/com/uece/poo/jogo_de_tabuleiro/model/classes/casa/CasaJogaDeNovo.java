package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaJogaDeNovo extends Casa{

    public CasaJogaDeNovo(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaJogaDeNovo(int index) {
        super(index);
    }
    
    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        jogador.setTentarNovamente(true);
        listener.onCasaAplicada(jogador.getNome() + " irá jogar novamente.");
    }
}
