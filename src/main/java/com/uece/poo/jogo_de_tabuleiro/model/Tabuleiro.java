package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaFactory;
import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.CasaSimples;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class Tabuleiro {

    private final List<Casa> casas;
    private int rodadaAtual;
    private final int quantidadeJogadores;
    private final List<Jogador> jogadores;
    private final int quantidadeCasas;
    private final HashMap<Integer, Class<? extends Casa>> casasEspeciais;
    private static Tabuleiro thisTabuleiro;

    private Tabuleiro(List<Jogador> jogadoresIniciais, int quantidadeCasas, HashMap<Integer, Class<? extends Casa>> casasEspeciais) {
        this.quantidadeCasas = quantidadeCasas;
        this.casasEspeciais = casasEspeciais;
        this.jogadores = new CopyOnWriteArrayList<>(jogadoresIniciais);
        this.quantidadeJogadores = jogadoresIniciais.size();
        casas = new CopyOnWriteArrayList<>();
        criarCasas(jogadores);
        rodadaAtual = 0;
    }

    public static Tabuleiro getInstance(List<Jogador> jogadoresIniciais, int quantidadeCasas, HashMap<Integer, Class<? extends Casa>> casasEspeciais) {
        if (thisTabuleiro == null) {
            thisTabuleiro = new Tabuleiro(jogadoresIniciais, quantidadeCasas, casasEspeciais);
        }
        return thisTabuleiro;
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

    private void criarCasas(List<Jogador> jogadores) {
        casas.add(0, new CasaSimples(0, jogadores));
        for (int i = 1; i < quantidadeCasas + 1; i++) {
            if (casasEspeciais.containsKey(i)) {
                casas.add(CasaFactory.getCasa(casasEspeciais.get(i), i));
                continue;
            }
            casas.add(CasaFactory.getCasa(CasaSimples.class, i));
        }
    }
}
