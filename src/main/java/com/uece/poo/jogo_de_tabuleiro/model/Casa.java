package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;

public class Casa {

    protected int index;
    protected ArrayList<Jogador> jogadores;

    public int getIndex() {
        return index;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void addJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
