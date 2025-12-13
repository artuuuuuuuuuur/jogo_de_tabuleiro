package com.uece.poo.jogo_de_tabuleiro.model.util;

import com.uece.poo.jogo_de_tabuleiro.model.classes.casa.*;

import java.util.*;

public final class CasasEspeciaisDescricao {

    private static final List<String> DESCRICOES = List.of(
            "Voltar 3 casas",
            "Andar 3 casas",
            "Jogar novamente",
            "Prisão",
            "Trocar de lugar",
            "Mudar tipo de jogador",
            "Voltar ao início"
    );

    private static final Map<String, Class<? extends Casa>> MAP_DESCRICAO_CLASSE = Map.of(
            "Voltar 3 casas", CasaAzar.class,
            "Andar 3 casas", CasaSorte.class,
            "Jogar novamente", CasaJogaDeNovo.class,
            "Prisão", CasaPrisao.class,
            "Trocar de lugar", CasaReversa.class,
            "Mudar tipo de jogador", CasaSurpresa.class,
            "Voltar ao início", CasaVoltarInicio.class
    );

    private static final Map<Class<? extends Casa>, String> MAP_CLASSE_DESCRICAO = criarMapaClasseDescricao();

    private CasasEspeciaisDescricao() {}

    private static Map<Class<? extends Casa>, String> criarMapaClasseDescricao() {
        Map<Class<? extends Casa>, String> invertido = new HashMap<>();
        MAP_DESCRICAO_CLASSE.forEach((descricao, classe) -> invertido.put(classe, descricao));
        return Collections.unmodifiableMap(invertido);
    }


    public static List<String> getDescricoes() {
        return DESCRICOES;
    }

    public static Class<? extends Casa> getClasse(String descricao) {
        return MAP_DESCRICAO_CLASSE.get(descricao);
    }

    public static String getDescricao(Class<? extends Casa> classe) {
        return MAP_CLASSE_DESCRICAO.get(classe);
    }
}
