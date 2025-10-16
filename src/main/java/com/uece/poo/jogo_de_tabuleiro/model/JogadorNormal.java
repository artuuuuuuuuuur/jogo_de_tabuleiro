package com.uece.poo.jogo_de_tabuleiro.model;

public class JogadorNormal extends Jogador {

    public JogadorNormal(String cor, String nome) {
        super(cor, nome);
    }

    public JogadorNormal(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas, dadosIguais);
    }

    @Override
    public void jogar() {
        dadosIguais = false;
        if (ativo) {
            jogarNovamente = true;
            while (jogarNovamente) {
                jogarNovamente = false;

                rolarDados();
                andar(dados[0] + dados[1]);
                vezesJogadas++;
                System.out.println(nome + " jogou!");

                if (dados[0] == dados[1]) {
                    dadosIguais = true;
                }
            }
        } else {
            ativo = true;
        }

    }

}
