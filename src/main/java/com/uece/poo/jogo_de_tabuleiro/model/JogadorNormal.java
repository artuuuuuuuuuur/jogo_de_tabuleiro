package com.uece.poo.jogo_de_tabuleiro.model;

public class JogadorNormal extends Jogador {

    public JogadorNormal(String cor, String nome) {
        super(cor, nome);
    }

    public JogadorNormal(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas);
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
