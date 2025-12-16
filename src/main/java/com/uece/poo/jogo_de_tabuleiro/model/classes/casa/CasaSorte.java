package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class CasaSorte extends Casa {

    public CasaSorte(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaSorte(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        if (isNotAzarado(jogador) && jogador.getPosicao() == index) {
            jogador.andar(3);
            Logger.log((jogador.getNome() + " andou 3 casas."));
        }
    }

    private static boolean isNotAzarado(Jogador jogador) {
        return jogador != null && !(jogador instanceof JogadorAzarado);
    }

}
