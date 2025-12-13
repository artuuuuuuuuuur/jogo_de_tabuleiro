package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

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
            jogarNovamente = true;
            while (jogarNovamente) {
                jogarNovamente = false;
                rolarDados();
                if (dados[0] + dados[1] <= 6) {
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
            dados[0] = 0;
            dados[1] = 0;
            System.out.println(nome + " agora pode jogar.");
        }
    }

}
