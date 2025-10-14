package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;

public class Tabuleiro {

    private final ArrayList<Casa> casas;
    private int rodadaAtual;
    private final int quantidadeJogadores;

    public Tabuleiro(int quantidadeJogadores) {
        casas = new ArrayList<>();
        for (int i = 0; i < 41; i++) {
            switch (i) {
                case 10:
                case 25:
                case 38:
                    casas.add(new CasaDesativar());
                    break;
                case 13:
                    casas.add(new CasaMudarTipo());
                    break;
                case 5:
                case 15:
                case 30:
                    casas.add(new CasaAndarTres());
                    break;
                case 17:
                case 27:
                    casas.add(new CasaVoltarInicio());
                    break;
                case 20:
                case 35:
                    casas.add(new CasaTrocar());
                    break;
                default:
                    casas.add(new Casa());
            }
        }
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
    
    public void moverJogador(Jogador jogador, int numCasas) {
        for (Casa casa : casas) {
            for (Jogador jogadorAtual : casa.getJogadores()) {
                if(jogador.equals(jogadorAtual)) {
                    jogadorAtual.andar(numCasas);
                    casas.get(jogadorAtual.getPosicao()).addJogador(jogadorAtual);
                    casa.removeJogador(jogadorAtual);
                }
            }
        }
    }
}