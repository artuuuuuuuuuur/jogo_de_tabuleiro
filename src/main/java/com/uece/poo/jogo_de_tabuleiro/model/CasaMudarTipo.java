package com.uece.poo.jogo_de_tabuleiro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CasaMudarTipo extends Casa {

    public CasaMudarTipo(int index, List<Jogador> jogadores) {
        super(index, jogadores);
    }

    @Override
    public void executarAcaoEspecial(Tabuleiro tabuleiro) {
        // iterar sobre copia para evitar ConcurrentModification
        List<Jogador> copia = new ArrayList<>(this.getJogadores());

        List<Replacement> replacements = new ArrayList<>();
        Random random = new Random();

        for (Jogador jogador : copia) {
            // evitar repetir efeito na mesma casa (se necessário)
            if (jogador.getLastCasaEspecialIndex() == this.getIndex()) {
                continue;
            }

            int tipo = random.nextInt(3);
            Jogador novo;
            switch (tipo) {
                case 0 ->
                    novo = new JogadorNormal(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                            jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                case 1 ->
                    novo = new JogadorComSorte(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                            jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                case 2 ->
                    novo = new JogadorAzarado(jogador.isAtivo(), jogador.getCor(), jogador.getNome(),
                            jogador.isJogarNovamente(), jogador.getPosicao(), jogador.getVezesJogadas(), jogador.isDadosIguais());
                default ->
                    throw new AssertionError();
            }

            replacements.add(new Replacement(jogador, novo));
        }

        // aplicar substituições na lista global de jogadores (CopyOnWriteArrayList permite set)
        for (Replacement r : replacements) {
            List<Jogador> listaGlobal = tabuleiro.getJogadoresGlobal(); // ideal: referência para a lista do Tabuleiro/Controller
            // se você não tem getJogadoresGlobal(), use tabuleiro.getJogadores() se existir
            int idx = listaGlobal.indexOf(r.oldPlayer);
            if (idx >= 0) {
                listaGlobal.set(idx, r.newPlayer);
                r.newPlayer.setLastCasaEspecialIndex(this.getIndex()); // marca que já foi processado aqui
                System.out.println(r.oldPlayer.getNome() + " mudou de tipo. Novo tipo: " + r.newPlayer.getClass());
            }
        }
    }

    // auxiliar para mapear substituições
    private static class Replacement {

        final Jogador oldPlayer;
        final Jogador newPlayer;

        Replacement(Jogador o, Jogador n) {
            oldPlayer = o;
            newPlayer = n;
        }
    }
}
