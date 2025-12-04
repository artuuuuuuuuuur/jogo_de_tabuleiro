package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;

public class CasaSorte extends Casa {

    public CasaSorte(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaSorte(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        for (Jogador jogador : jogadores) {
            if (isNotAzarado(jogador)) {
                if (jogador.getPosicao() == index) {
                    jogador.andar(3);
                    System.out.println(jogador.getNome() + " andou 3 casas.");
                }
            }
        }
    }

    private static boolean isNotAzarado(Jogador jogador) {
        return jogador != null && !(jogador instanceof JogadorAzarado);
    }

}
