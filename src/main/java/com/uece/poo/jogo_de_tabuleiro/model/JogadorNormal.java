package com.uece.poo.jogo_de_tabuleiro.model;

public class JogadorNormal extends Jogador {

    public JogadorNormal(String cor) {
        super(cor);
    }

    public JogadorNormal(boolean ativo, String cor, boolean jogarNovamente, int posicao, int vezesJogadas) {
        super(ativo, cor, jogarNovamente, posicao, vezesJogadas);
    }

    @Override
    public void jogar() {
        if (ativo) {
            jogarNovamente = true;
            while (jogarNovamente) {
                jogarNovamente = false;

                rolarDados();
                andar(dados[0] + dados[1]);
                vezesJogadas++;

                if (dados[0] == dados[1]) {
                    jogarNovamente = true;
                }
            }
        } else {
            ativo = true;
        }

    }

}
