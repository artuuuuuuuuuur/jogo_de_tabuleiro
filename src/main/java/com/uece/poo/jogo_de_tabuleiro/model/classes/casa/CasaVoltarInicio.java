package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaVoltarInicio extends Casa {

    public CasaVoltarInicio(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro) {
        // iterar sobre copia dos jogadores da casa atual
        List<Jogador> presentes = List.copyOf(this.getJogadores());
        for (Jogador jogador : presentes) {
            Jogador alvo = jogador.getJogadorAlvo();
            if (alvo != null && alvo.getLastCasaEspecialIndex() != this.getIndex()) {
                alvo.setPosicao(0);
                alvo.setLastCasaEspecialIndex(this.getIndex());
                System.out.println(alvo.getNome() + " voltou para o início.");
            }
        }
    }
}
