package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.Random;

public abstract class Jogador {

    protected int posicao, vezesJogadas;
    protected int[] dados = new int[2];
    protected String cor, nome;
    protected boolean ativo, jogarNovamente, dadosIguais;
    protected Jogador jogadorAlvo;

    public Jogador(String cor, String nome) {
        this.cor = cor;
        this.ativo = true;
        this.posicao = 0;
        this.vezesJogadas = 0;
        this.jogarNovamente = true;
        this.nome = nome;
        this.dadosIguais = false;
    }

    public Jogador(boolean ativo, String cor, String nome, boolean jogarNovamente, int posicao, int vezesJogadas, boolean dadosIguais) {
        this.ativo = ativo;
        this.cor = cor;
        this.jogarNovamente = jogarNovamente;
        this.posicao = posicao;
        this.vezesJogadas = vezesJogadas;
        this.nome = nome;
        this.dadosIguais = dadosIguais;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
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

    public boolean isAtivo() {
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
        if (posicao > 40) {
            posicao = 40;
        }
    }

    public void rolarDados() {
        Random random = new Random();
        for (int i = 0; i < dados.length; i++) {
            dados[i] = random.nextInt(6) + 1;
        }
    }

    public void voltarAoInicio() {
        posicao = 0;
    }

    public abstract void jogar();

    public Jogador getJogadorAlvo() {
        return jogadorAlvo;
    }

    public void setJogadorAlvo(Jogador jogadorAlvo) {
        this.jogadorAlvo = jogadorAlvo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDadosIguais() {
        return dadosIguais;
    }
}
