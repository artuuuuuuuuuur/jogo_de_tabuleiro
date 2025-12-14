package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaVoltarInicio extends Casa {

    public CasaVoltarInicio(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    public CasaVoltarInicio(int index) {
        super(index);
    }

    @Override
    public void aplicarRegra(Tabuleiro tabuleiro, Jogador jogador) {
        List<Jogador> presentes = List.copyOf(this.getJogadores());
        Jogador alvo = jogador.getJogadorAlvo();
        if (alvo != null && alvo.getLastCasaEspecialIndex() != this.getIndex()) {
            alvo.setPosicao(0);
            alvo.setLastCasaEspecialIndex(this.getIndex());
            listener.onCasaAplicada(alvo.getNome() + " voltou para o início.");
        }
    }
}
