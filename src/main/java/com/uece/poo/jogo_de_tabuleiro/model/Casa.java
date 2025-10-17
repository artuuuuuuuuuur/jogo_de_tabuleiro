package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.List;

public class Casa {

    protected int index;
    protected List<Jogador> jogadores;

    public Casa(int index, List<Jogador> jogadores) {
        this.index = index;
        this.jogadores = jogadores;
    }

    public int getIndex() {
        return index;
    }

    public void clearJogadores() {
        jogadores.clear();
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void addJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
