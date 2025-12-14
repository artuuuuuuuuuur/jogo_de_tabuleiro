package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class CasaReversa extends Casa {

    public CasaReversa(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaReversa(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        for (Casa casa : tabuleiro.getCasas()) {
            if (!casa.getJogadores().isEmpty()) {
                List<Jogador> jogadoresCasaSnapshot = List.copyOf(casa.getJogadores());
                for (Jogador jogadorAlvo : jogadoresCasaSnapshot) {
                    if (!jogadorAlvo.equals(jogador)) {
                        int posTemp = jogadorAlvo.getPosicao();
                        jogadorAlvo.setPosicao(jogador.getPosicao());
                        jogador.setPosicao(posTemp);
                        Logger.log((jogador.getNome() + " trocou com " + jogadorAlvo.getNome()));
                    }
                }
            }

        }
    }
}
