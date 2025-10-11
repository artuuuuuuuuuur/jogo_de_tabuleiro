package com.uece.poo.jogo_de_tabuleiro.model;

public class JogadorComSorte extends Jogador {

    public JogadorComSorte(String cor) {
        super(cor);
    }

    public JogadorComSorte(boolean ativo, String cor, boolean jogarNovamente, int posicao, int vezesJogadas) {
        super(ativo, cor, jogarNovamente, posicao, vezesJogadas);
    }

    @Override
    public void jogar() {
        if (ativo) {
            jogarNovamente = true;
            while (jogarNovamente) {
                jogarNovamente = false;
                rolarDados();

                if (dados[0] + dados[1] >= 7) {
                    andar(dados[0] + dados[1]);
                    vezesJogadas++;
                } else {
                    jogarNovamente = true;
                }

                if (dados[0] == dados[1]) {
                    jogarNovamente = true;
                }

            }
        } else {
            ativo = true;
        }
    }

}
