package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaReversa extends Casa {

    public CasaReversa(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaReversa(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        List<Jogador> jogadoresSnapshot = List.copyOf(jogadores);
        List<Casa> casasSnapshot = List.copyOf(tabuleiro.getCasas());

        for (Jogador jogadorUser : jogadoresSnapshot) {
            for (Casa casa : casasSnapshot) {
                if (!casa.getJogadores().isEmpty()) {
                    List<Jogador> jogadoresCasaSnapshot = List.copyOf(casa.getJogadores());
                    for (Jogador jogador : jogadoresCasaSnapshot) {
                        if (!jogador.equals(jogadorUser)) {
                            int posTemp = jogador.getPosicao();
                            jogador.setPosicao(jogadorUser.getPosicao());
                            jogadorUser.setPosicao(posTemp);
                            System.out.println(jogadorUser.getNome() + " trocou com " + jogador.getNome());
                        }
                    }
                }
            }
        }
    }

}
