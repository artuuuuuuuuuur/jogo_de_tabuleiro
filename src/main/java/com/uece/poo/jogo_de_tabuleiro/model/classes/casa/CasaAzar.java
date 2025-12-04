package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorSortudo;

public class CasaAzar extends Casa {
    
    public CasaAzar(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }
    
    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            if (isNotSortudo(jogador)) {
                if (jogador.getPosicao() == index) {
                    jogador.andar(3);
                    System.out.println(jogador.getNome() + " voltou 3 casas.");
                }
            }
        }
    }
    
    private static boolean isNotSortudo(Jogador jogador) {
        return jogador != null && !(jogador instanceof JogadorSortudo);
    }
}
