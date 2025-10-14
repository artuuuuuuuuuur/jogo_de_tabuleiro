package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;

public class Tabuleiro {

    private final ArrayList<Casa> casas;
    private int rodadaAtual;
    private final int quantidadeJogadores;

    public Tabuleiro(int quantidadeJogadores) {
        casas = new ArrayList<>();
        rodadaAtual = 0;
        this.quantidadeJogadores = quantidadeJogadores;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }

    public int getRodadaAtual() {
        return rodadaAtual;
    }

    public int getQuantidadeJogadores() {
        return quantidadeJogadores;
    }

    public void setRodadaAtual(int rodadaAtual) {
        this.rodadaAtual = rodadaAtual;
    }
}