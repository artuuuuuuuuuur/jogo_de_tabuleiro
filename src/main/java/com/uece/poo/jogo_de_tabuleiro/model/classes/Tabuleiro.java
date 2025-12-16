package com.uece.poo.jogo_de_tabuleiro.model.classes;

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

    public List<Jogador> getJogadores() {
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

    public void atualizarJogadores(List<Jogador> jogadores) {
        for (Casa casa : this.getCasas()) {
            casa.clearJogadores();
        }

        for (Jogador jogador : jogadores) {
            Casa casa = this.getCasa(jogador.getPosicao());
            if (casa != null) {
                casa.addJogador(jogador);
            }
        }
    }

    private void criarCasas(List<Jogador> jogadores) {
        casas.clear();
        casas.addFirst(CasaFactory.getCasa(CasaSimples.class, 0, jogadores));
        for (int i = 1; i <= quantidadeCasas; i++) {
            if (casasEspeciais.containsKey(i)) {
                casas.add(CasaFactory.getCasa(casasEspeciais.get(i), i));
                continue;
            }
            casas.add(CasaFactory.getCasa(CasaSimples.class, i));
        }
    }
}
