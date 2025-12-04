package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.Tabuleiro;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public abstract class Casa {

    protected int index;
    protected List<Jogador> jogadores;

    public Casa(int index, List<Jogador> jogadores) {
        this.index = index;
        this.jogadores = jogadores;
    }
    
    public Casa(int index) {
        this.index = index;
        this.jogadores = new CopyOnWriteArrayList<>();
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

    public abstract void aplicarRegra(Tabuleiro tabuleiro);

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
