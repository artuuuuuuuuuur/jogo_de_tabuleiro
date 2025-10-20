package com.uece.poo.jogo_de_tabuleiro.model;

public class JogadorComSorte extends Jogador {
    
    public JogadorComSorte(String cor, String nome) {
        super(cor, nome);
        tipo = "Com sorte";
    }

    public JogadorComSorte(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas, dadosIguais);
        tipo = "Com sorte";
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
                    System.out.println(nome + " jogou!");
                    dadosIguais = false;
                } else {
                    jogarNovamente = true;
                }

                if (dados[0] == dados[1]) {
                    dadosIguais = true;
                }

            }
        } else {
            ativo = true;
        }
    }

}
