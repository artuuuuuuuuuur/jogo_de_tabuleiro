package com.uece.poo.jogo_de_tabuleiro.model.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorAzarado;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorNormal;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.JogadorSortudo;
import com.uece.poo.jogo_de_tabuleiro.model.util.ExceptionModal;
import com.uece.poo.jogo_de_tabuleiro.model.util.JogoListener;


public class Jogo {

    private boolean modoDebug;
    private int numJogadores;
    private HashMap<Integer, Class<? extends Casa>> casasEspeciais;
    private int numCasas;
    private List<Jogador> jogadores;
    private Jogador jogadorVencedor;
    private Set<String> coresEscolhidas;
    private boolean jogadoresNormais;
    private boolean jogadoresSortudos;
    private boolean jogadoresAzarados;
    private Jogador jogadorAtual;
    private JogoListener listener;
    private Tabuleiro tabuleiro;
    

    public Jogo() {
        jogadores = new CopyOnWriteArrayList<>();
        coresEscolhidas = new HashSet<>();
        jogadoresNormais = false;
        jogadoresAzarados = false;
        jogadoresSortudos = false;
    }

    public void configTabuleiro(int quantidadeCasas, HashMap<Integer, Class<? extends Casa>> casasEspeciais) throws IllegalArgumentException {
        if (quantidadeCasas <= 0) {
            throw new IllegalArgumentException("A quantidade de casas deve ser maior que 0.");
        }
        this.numCasas = quantidadeCasas;
        this.casasEspeciais = casasEspeciais;
        this.tabuleiro = Tabuleiro.getInstance(jogadores, quantidadeCasas, casasEspeciais);
    }

    public void configJogadores(List<Jogador> jogadores) throws IllegalArgumentException {
        validarJogadores(jogadores);
        this.numJogadores = jogadores.size();
        this.jogadores = jogadores;
    }

    public boolean start() {
        Thread.ofVirtual().start(this::loopJogo);
        return true;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    private void loopJogo() {
        int index = 0;
        while (!isGameOver()) {
            Jogador jogador = jogadores.get(index);
            jogarTurno(jogador);
            if (isGameOver()) {
                listener.onVitoria(jogador);
                break;
            }
            index = (index + 1) % jogadores.size();

        }
    }

    private void jogarTurno(Jogador jogador) {
        listener.onTurnoIniciado(jogador);
        int valor = jogarDados(jogador);
        listener.onDepoisDeJogarDados(jogador);

        jogador.setPosicao(jogador.getPosicao() + valor);
        listener.onMovimentoConcluido(jogador);

        aplicarCasa(jogador);
        if(jogador.isDadosIguais()) {
            jogarTurno(jogador);
        }
    }

    private Integer jogarDados(Jogador jogador) {
        if (modoDebug) {
            CompletableFuture<Integer> resultadoDebug = new CompletableFuture<>();
            listener.onDebugMode(jogador, resultadoDebug);

            try{
                return resultadoDebug.get();
            } catch (ExecutionException | InterruptedException e) {
                ExceptionModal.popUp(e.getMessage());
            }
        }
        jogador.jogar();
        return (jogador.getDados()[0] + jogador.getDados()[1]);
    }

    private void validarJogadores(List<Jogador> jogadores) throws IllegalArgumentException {
        int count = 0;
        verificarTipos(jogadores);

        if (jogadoresNormais) {
            count++;
        }
        if (jogadoresSortudos) {
            count++;
        }
        if (jogadoresAzarados) {
            count++;
        }

        if (count < 2) {
            throw new IllegalArgumentException("É necessário que ao menos dois jogadores sejam de tipos diferentes");
        }

        for (Jogador jogador : jogadores) {
            if (!coresEscolhidas.add(jogador.getCor())) {
                throw new IllegalArgumentException("Dois jogadores não podem ter a mesma cor!");
            }
        }
    }

    private void verificarTipos(List<Jogador> jogadores) {
        for (Jogador jogador : jogadores) {
            switch (jogador) {
                case JogadorNormal _ -> jogadoresNormais = true;
                case JogadorAzarado _ -> jogadoresAzarados = true;
                case JogadorSortudo _ -> jogadoresSortudos = true;
                default -> {}
            }
        }
    }

    private void aplicarCasa(Jogador jogador) {
        Casa casa = tabuleiro.getCasa(jogador.getPosicao());
        if (casa != null) {
            casa.aplicarRegra(tabuleiro);
            listener.onCasaAplicada(jogador, casa);
        }
    }

    private boolean isGameOver() {
        for (Jogador jogador : jogadores) {
            if (jogador.getPosicao() >= 40) {
                jogadorVencedor = jogador;
                return true;
            }
        }
        return false;
    }
}
