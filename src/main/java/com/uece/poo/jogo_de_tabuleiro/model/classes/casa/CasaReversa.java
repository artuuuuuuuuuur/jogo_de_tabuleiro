package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaReversa extends Casa {

    public CasaReversa(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaReversa(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        List<Jogador> jogadoresSnapshot = List.copyOf(jogadores);
        List<Casa> casasSnapshot = List.copyOf(tabuleiro.getCasas());
        for (Casa casa : casasSnapshot) {
            if (!casa.getJogadores().isEmpty()) {
                List<Jogador> jogadoresCasaSnapshot = List.copyOf(casa.getJogadores());
                for (Jogador jogadorAlvo : jogadoresCasaSnapshot) {
                    if (!jogadorAlvo.equals(jogador)) {
                        int posTemp = jogadorAlvo.getPosicao();
                        jogadorAlvo.setPosicao(jogador.getPosicao());
                        jogador.setPosicao(posTemp);
                        listener.onCasaAplicada(jogador.getNome() + " trocou com " + jogadorAlvo.getNome());
                    }
                }
            }

        }
    }
}
