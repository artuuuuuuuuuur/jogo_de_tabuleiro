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
import com.uece.poo.jogo_de_tabuleiro.model.util.CasaListener;
import com.uece.poo.jogo_de_tabuleiro.model.util.view.ExceptionModal;
import com.uece.poo.jogo_de_tabuleiro.model.util.JogoListener;


public class Jogo {

    private boolean modoDebug;
    private List<Jogador> jogadores;
    private Set<String> coresEscolhidas;
    private boolean jogadoresNormais;
    private boolean jogadoresSortudos;
    private boolean jogadoresAzarados;
    private JogoListener listener;
    private Tabuleiro tabuleiro;


    public Jogo() {
        jogadores = new CopyOnWriteArrayList<>();
        coresEscolhidas = new HashSet<>();
        jogadoresNormais = false;
        jogadoresAzarados = false;
        jogadoresSortudos = false;
    }

    public void configTabuleiro(int quantidadeCasas, HashMap<Integer, Class<? extends Casa>> casasEspeciais, boolean modoDebug) throws IllegalArgumentException {
        if (quantidadeCasas <= 0) {
            throw new IllegalArgumentException("A quantidade de casas deve ser maior que 0.");
        }
        this.tabuleiro = Tabuleiro.getInstance(jogadores, quantidadeCasas, casasEspeciais);
        this.modoDebug = modoDebug;
    }

    public void configJogadores(List<Jogador> jogadores) {
        validarJogadores(jogadores);
        this.jogadores.clear();
        this.jogadores.addAll(jogadores);
    }


    public boolean isModoDebug() {
        return modoDebug;
    }

    public void start() {
        Thread.ofVirtual().start(this::loopJogo);
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    private void loopJogo() {
        int index = 0;

        while (!isGameOver()) {
            Jogador jogador = jogadores.get(index);

            if (!jogador.isAtivo()) {
                jogador.jogar();
                index = (index + 1) % jogadores.size();
                continue;
            }

            do {
                jogarTurno(jogador);
                if (isGameOver()) {
                    listener.onVitoria(jogador);
                    return;
                }
            } while (jogador.isJogarNovamente());

            index = (index + 1) % jogadores.size();
        }
    }


    public List<Jogador> getJogadores() {
        return jogadores;
    }

    private void jogarTurno(Jogador jogador) {
        CompletableFuture<Integer> resultadoDebug = new CompletableFuture<>();

        listener.onTurnoIniciado(jogador);

        int valor = jogarDados(jogador, resultadoDebug);

        int novaPosicao = jogador.getPosicao() + valor;
        novaPosicao = Math.min(novaPosicao, (tabuleiro.getCasas().size() - 1));
        jogador.setPosicao(novaPosicao);
        jogador.setJogarNovamente(false);
        jogador.setVezesJogadas(jogador.getVezesJogadas()+1);
        tabuleiro.atualizarJogadores(jogadores);
        aplicarCasa(jogador);
        listener.onMovimentoConcluido(jogador);
    }


    private Integer jogarDados(Jogador jogador, CompletableFuture<Integer> resultadoDebug) {
        if (modoDebug) {
            listener.onDebugMode(jogador, resultadoDebug);
            try{
                return resultadoDebug.get();
            } catch (ExecutionException | InterruptedException e) {
                ExceptionModal.popUp(e.getMessage());
            }
        }
        jogador.jogar();
        listener.onNormalMode(jogador);
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
                default -> ExceptionModal.popUp("Tipo não reconhecido.");
            }
        }
    }

    private void aplicarCasa(Jogador jogador) {
        Casa casa = tabuleiro.getCasa(jogador.getPosicao());
        if (casa != null) {
            casa.setListener((CasaListener) listener);
            casa.aplicarRegra(tabuleiro, jogador);
        }
    }

    private boolean isGameOver() {
        for (Jogador jogador : jogadores) {
            if (jogador.getPosicao() >= tabuleiro.getCasas().size()-1) {
                return true;
            }
        }
        return false;
    }

    public void setListener(JogoListener listener) {
        this.listener = listener;
    }
}
