package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaSorte;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaPrisao;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaSurpresa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaSimples;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaReversa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaVoltarInicio;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class Tabuleiro {

    private final ArrayList<Casa> casas;
    private int rodadaAtual;
    private final int quantidadeJogadores;
    private final List<Jogador> jogadores;

    public Tabuleiro(List<Jogador> jogadoresIniciais) {
        casas = new ArrayList<>();
        casas.add(0, new CasaSimples(0, jogadoresIniciais));
        for (int i = 1; i < 41; i++) {
            switch (i) {
                case 10, 25, 38 ->
                    casas.add(new CasaPrisao(i, new ArrayList<>()));
                case 13 ->
                    casas.add(new CasaSurpresa(i, new ArrayList<>()));
                case 5, 15, 30 ->
                    casas.add(new CasaSorte(i, new ArrayList<>()));
                case 17, 27 ->
                    casas.add(new CasaVoltarInicio(i, new ArrayList<>()));
                case 20, 35 ->
                    casas.add(new CasaReversa(i, new ArrayList<>()));
                default ->
                    casas.add(new CasaSimples(i, new ArrayList<>()));
            }
        }
        rodadaAtual = 0;
        this.quantidadeJogadores = jogadoresIniciais.size();
        this.jogadores = new CopyOnWriteArrayList<>(jogadoresIniciais);
    }

    public List<Casa> getCasas() {
        return casas;
    }
    
    public List<Jogador> getJogadoresGlobal() {
        return jogadores;
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
