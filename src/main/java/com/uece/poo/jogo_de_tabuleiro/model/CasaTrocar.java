package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.List;

public class CasaTrocar extends Casa {

    public CasaTrocar(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        List<Jogador> jogadoresSnapshot = List.copyOf(jogadores);
        List<Casa> casasSnapshot = List.copyOf(tabuleiro.getCasas());

        for (Jogador jogadorUser : jogadoresSnapshot) {
            for (Casa casa : casasSnapshot) {
                if(!casa.getJogadores().isEmpty()) {
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
