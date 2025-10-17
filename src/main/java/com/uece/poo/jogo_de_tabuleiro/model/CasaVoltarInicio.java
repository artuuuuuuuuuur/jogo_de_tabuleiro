package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.List;

public class CasaVoltarInicio extends Casa {

    public CasaVoltarInicio(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        // iterar sobre copia dos jogadores da casa atual
        List<Jogador> presentes = List.copyOf(this.getJogadores());
        for (Jogador jogador : presentes) {
            // supondo jogadorUser é quem causa o efeito (pode ser o próprio presente)
            Jogador alvo = jogador.getJogadorAlvo();
            if (alvo != null && alvo.getLastCasaEspecialIndex() != this.getIndex()) {
                alvo.setPosicao(0);
                alvo.setLastCasaEspecialIndex(this.getIndex());
                System.out.println(alvo.getNome() + " voltou para o início.");
            }
        }
    }
}
