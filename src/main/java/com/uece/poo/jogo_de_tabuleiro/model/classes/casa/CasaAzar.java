package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorSortudo;

public class CasaAzar extends Casa {
    
    public CasaAzar(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaAzar(int index) {
        super(index);
    }
    
    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        if (isNotSortudo(jogador)) {
            if (jogador.getPosicao() == index) {
                jogador.andar(3);
                listener.onCasaAplicada(jogador.getNome() + " voltou 3 casas.");
            }
        }
    }
    
    private static boolean isNotSortudo(Jogador jogador) {
        return jogador != null && !(jogador instanceof JogadorSortudo);
    }
}
