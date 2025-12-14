package com.uece.poo.jogo_de_tabuleiro.model.classes.jogador;

import java.util.Random;

public abstract class Jogador {

    protected int posicao, vezesJogadas;
    protected int[] dados = new int[2];
    protected String cor, nome;
    protected boolean ativo, tentarNovamente, jogarNovamente;
    protected Jogador jogadorAlvo;
    protected int lastCasaEspecialIndex = -1;
    protected String tipo;
    protected int quantCasas;
    protected int moedas;

    public Jogador(String cor, String nome, int quantCasas) {
        this.cor = cor;
        this.ativo = true;
        this.posicao = 0;
        this.vezesJogadas = 0;
        this.tentarNovamente = true;
        this.nome = nome;
        this.jogarNovamente = false;
        this.quantCasas = quantCasas;
    }

    public Jogador(boolean ativo, String cor, String nome, boolean tentarNovamente, int posicao, int vezesJogadas, boolean jogarNovamente, int quantCasas) {
        this.ativo = ativo;
        this.cor = cor;
        this.tentarNovamente = tentarNovamente;
        this.posicao = posicao;
        this.vezesJogadas = vezesJogadas;
        this.nome = nome;
        this.jogarNovamente = jogarNovamente;
        this.quantCasas = quantCasas;
    }

    public int getQuantCasas() {
        return quantCasas;
    }

    public int getLastCasaEspecialIndex() {
        return lastCasaEspecialIndex;
    }

    public void setLastCasaEspecialIndex(int idx) {
        this.lastCasaEspecialIndex = idx;
    }

    public void setPosicao(int pos) {
        if (this.posicao != pos) {
            this.lastCasaEspecialIndex = -1;
        }
        this.posicao = pos;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isTentarNovamente() {
        return tentarNovamente;
    }

    public void andar(int numCasas) {
        posicao = posicao + numCasas;
        if(posicao >= quantCasas-1) {
            posicao = quantCasas-1;
        }
    }

    protected void rolarDados() {
        Random random = new Random();
        for (int i = 0; i < dados.length; i++) {
            dados[i] = random.nextInt(6) + 1;
        }
    }

    public int getMoedas() {return moedas;}

    public void setMoedas(int moedas) {
        this.moedas = moedas;
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

    public boolean isJogarNovamente() {
        return jogarNovamente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTentarNovamente(boolean tentarNovamente) {
        this.tentarNovamente = tentarNovamente;
    }

    public void setVezesJogadas(int vezesJogadas) {
        this.vezesJogadas = vezesJogadas;
    }

    public void setJogarNovamente(boolean jogarNovamente) {
        this.jogarNovamente = jogarNovamente;
    }

    public void setQuantCasas(int quantCasas) {
        this.quantCasas = quantCasas;
    }
}
