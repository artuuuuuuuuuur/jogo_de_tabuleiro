package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;
import com.uece.poo.jogo_de_tabuleiro.model.util.ExceptionModal;

public class CasaFactory {

    public static Casa getCasa(Class<? extends Casa> casaType, int index) {
        try {
            return casaType.getConstructor(int.class).newInstance(index);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            ExceptionModal.popUp(e.getMessage());
            return null;
        }
    }

    public static Casa getCasa(Class<? extends Casa> casaType, int index, List<Jogador> jogadores) {
        Casa newCasa = getCasa(casaType, index);
        newCasa.setJogadores(jogadores);
        return newCasa;
    }
}
