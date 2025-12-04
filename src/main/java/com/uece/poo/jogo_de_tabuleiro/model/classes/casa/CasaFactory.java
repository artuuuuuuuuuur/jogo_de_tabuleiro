package com.uece.poo.jogo_de_tabuleiro.model.classes.casa;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.uece.poo.jogo_de_tabuleiro.model.classes.jogador.Jogador;

public class CasaFactory {

    public Casa getCasa(Class<? extends Casa> casaType, int index, List<Jogador> jogadores) {
        try {
            return casaType.getConstructor(int.class, List.class).newInstance(index, jogadores);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
