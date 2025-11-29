package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tabuleiro {

    private final ArrayList<Casa> casas;
    private int rodadaAtual;
    private final int quantidadeJogadores;
    private final List<Jogador> jogadoresGlobal;

    public Tabuleiro(List<Jogador> jogadoresIniciais) {
        casas = new ArrayList<>();
        casas.add(0, new Casa(0, jogadoresIniciais));
        for (int i = 1; i < 41; i++) {
            switch (i) {
                case 10, 25, 38 ->
                    casas.add(new CasaDesativar(i, new ArrayList<>()));
                case 13 ->
                    casas.add(new CasaMudarTipo(i, new ArrayList<>()));
                case 5, 15, 30 ->
                    casas.add(new CasaAndarTres(i, new ArrayList<>()));
                case 17, 27 ->
                    casas.add(new CasaVoltarInicio(i, new ArrayList<>()));
                case 20, 35 ->
                    casas.add(new CasaTrocar(i, new ArrayList<>()));
                default ->
                    casas.add(new Casa(i, new ArrayList<>()));
            }
        }
        rodadaAtual = 0;
        this.quantidadeJogadores = jogadoresIniciais.size();
        this.jogadoresGlobal = new CopyOnWriteArrayList<>(jogadoresIniciais);
    }

    public List<Casa> getCasas() {
        return casas;
    }
    
    public List<Jogador> getJogadoresGlobal() {
        return jogadoresGlobal;
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

    public Casa getCasa(int i) {
        return casas.get(i);
    }

    public void moverJogador(Jogador jogador, int numCasas) {
        for (Casa casa : casas) {
            for (Jogador jogadorAtual : casa.getJogadores()) {
                if (jogador.equals(jogadorAtual)) {
                    jogadorAtual.andar(numCasas);
                    casas.get(jogadorAtual.getPosicao()).addJogador(jogadorAtual);
                    casa.removeJogador(jogadorAtual);
                }
            }
        }
    }
}
