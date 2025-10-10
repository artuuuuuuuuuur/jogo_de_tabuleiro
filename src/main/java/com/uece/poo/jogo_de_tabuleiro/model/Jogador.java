package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.Random;

public abstract class Jogador {

    protected int posicao, vezesJogadas;
    protected int[] dados = new int[2];
    protected String cor;
    protected boolean ativo, jogarNovamente;

    public Jogador(String cor) {
        this.cor = cor;
        this.ativo = true;
        this.posicao = 0;
        this.vezesJogadas = 0;
        this.jogarNovamente = true;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getVezesJogadas() {
        return vezesJogadas;
    }

    public int[] getDados() {
        return dados;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean ativo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isJogarNovamente() {
        return jogarNovamente;
    }

    public void andar(int numCasas) {
        posicao = posicao + numCasas;
    }

    public void rolarDados() {
        Random random = new Random();
        for (int i = 0; i < dados.length; i++) {
            dados[i] = random.nextInt(6) + 1;
        }
    }

    public abstract void jogar();
}
