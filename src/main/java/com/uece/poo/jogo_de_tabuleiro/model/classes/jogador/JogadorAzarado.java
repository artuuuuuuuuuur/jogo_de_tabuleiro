package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String cor, String nome) {
        super(cor, nome);
        tipo = "Azarado";
    }

    public JogadorAzarado(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais) {
        super(ativo, cor, nome, jogarNovamente, posicao, vezesJogadas, dadosIguais);
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
        }
    }

}
