package com.uece.poo.jogo_de_tabuleiro.model;

public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String cor, String nome) {
        super(cor, nome);
    }

    public JogadorAzarado(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas);
    }

    @Override
    public void jogar() {
        if (ativo) {
            jogarNovamente = true;
            while (jogarNovamente) {
                jogarNovamente = false;
                rolarDados();
                if (dados[0] + dados[1] <= 6) {
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
