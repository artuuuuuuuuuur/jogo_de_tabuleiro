package com.uece.poo.jogo_de_tabuleiro.model.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.Casa;
import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.ExceptionModal;

public class Jogo {

    private boolean modoDebug;
    private int numJogadores;
    private HashMap<Integer, Class<? extends Casa>> casasEspeciais;
    private int numCasas;
    private List<Jogador> jogadores;
    private Set<String> coresEscolhidas;
    private boolean jogadoresNormais;
    private boolean jogadoresComSorte;
    private boolean jogadoresAzarados;

    public Jogo() {
        jogadores = new CopyOnWriteArrayList<>();
        coresEscolhidas = new HashSet<>();
        jogadoresNormais = false;
        jogadoresAzarados = false;
        jogadoresComSorte = false;
    }

    public void configTabuleiro(int quantidadeCasas, HashMap<Integer, Class<? extends Casa>> casasEspeciais) throws IllegalArgumentException {
        if (quantidadeCasas <= 0) {
            throw new IllegalArgumentException("A quantidade de casas deve ser maior que 0.");
        }

        this.numCasas = quantidadeCasas;
        this.casasEspeciais = casasEspeciais;
    }
    
    public void configJogadores(int numJogadores) throws  IllegalArgumentException {
        int count = 0;

        if (jogadoresNormais) {
            count++;
        }
        if (jogadoresComSorte) {
            count++;
        }
        if (jogadoresAzarados) {
            count++;
        }

        if (count < 2) {
            throw  new IllegalArgumentException("É necessário que ao menos dois jogadores sejam de tipos diferentes");
        }
        
        this.numJogadores = numJogadores;
    }
}
