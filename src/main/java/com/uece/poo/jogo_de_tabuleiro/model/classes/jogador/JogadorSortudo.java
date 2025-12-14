package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

import com.uece.poo.jogo_de_tabuleiro.model.util.view.Logger;

public class JogadorSortudo extends Jogador {
    
    public JogadorSortudo(String cor, String nome, int quantCasas) {
        super(cor, nome, quantCasas);
        tipo = "Com sorte";
    }

    public JogadorSortudo(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais, int quantCasas) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas, dadosIguais, quantCasas);
        tipo = "Com sorte";
    }

    @Override
    public void jogar() {
        if (ativo) {
            tentarNovamente = true;
            while (tentarNovamente) {
                tentarNovamente = false;
                rolarDados();

                if (dados[0] + dados[1] >= 7) {
                    vezesJogadas++;
                    jogarNovamente = false;
                } else {
                    tentarNovamente = true;
                }
            }
        } else {
            dados[0] = 0;
            dados[1] = 0;
            ativo = true;
            Logger.log(nome + " agora pode jogar.");
        }
    }

}
