package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

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
            dados[0] = 0;
            dados[1] = 0;
            ativo = true;
            System.out.println(nome + " agora pode jogar.");
        }
    }

}
