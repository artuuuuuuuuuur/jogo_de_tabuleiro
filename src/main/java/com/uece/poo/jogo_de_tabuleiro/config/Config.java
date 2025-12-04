package com.uece.poo.jogo_de_tabuleiro.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private Config() {
        throw new IllegalStateException("Config class.");
    }

    private static Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getResourceAsStream("/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load configuration file", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
