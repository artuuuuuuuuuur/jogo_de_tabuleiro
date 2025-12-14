package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String cor, String nome, int quantCasas) {
        super(cor, nome, quantCasas);
        tipo = "Azarado";
    }

    public JogadorAzarado(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais, int quantCasas) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas, dadosIguais, quantCasas);
        tipo = "Azarado";
    }

    @Override
    public void jogar() {
        if (ativo) {
            tentarNovamente = true;
            while (tentarNovamente) {
                tentarNovamente = false;
                rolarDados();
                if (dados[0] + dados[1] <= 6) {
                    vezesJogadas++;
                    jogarNovamente = false;
                } else {
                    tentarNovamente = true;
                }
            }
        } else {
            ativo = true;
            dados[0] = 0;
            dados[1] = 0;
            Logger.log(nome + " agora pode jogar.");
        }
    }

}
