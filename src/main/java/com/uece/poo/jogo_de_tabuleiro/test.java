package com.uece.poo.jogo_de_tabuleiro;

public class test {

    public static void main(String[] args) {
        // Cria jogadores de diferentes tipos
        Jogador j1 = new JogadorComSorte("Vermelho");
        Jogador j2 = new JogadorAzarado("Azul");
        Jogador j3 = new JogadorNormal("Verde");

        // Simula 5 rodadas para cada jogador
        for (int rodada = 1; rodada <= 5; rodada++) {
            System.out.println("\n===== RODADA " + rodada + " =====");

            // Jogador com sorte
            System.out.println("\n>> " + j1.getCor() + " (Sorte) começa a jogar:");
            jogarRodada(j1);

            // Jogador azarado
            System.out.println("\n>> " + j2.getCor() + " (Azar) começa a jogar:");
            jogarRodada(j2);

            // Jogador normal
            System.out.println("\n>> " + j3.getCor() + " (Normal) começa a jogar:");
            jogarRodada(j3);
        }

        // Mostra o resumo final
        System.out.println("\n===== RESULTADO FINAL =====");
        System.out.println(j1.getCor() + " (Sorte) jogou " + j1.getVezesJogadas() + " vezes e está na casa " + j1.getPosicao());
        System.out.println(j2.getCor() + " (Azar) jogou " + j2.getVezesJogadas() + " vezes e está na casa " + j2.getPosicao());
        System.out.println(j3.getCor() + " (Normal) jogou " + j3.getVezesJogadas() + " vezes e está na casa " + j3.getPosicao());
    }

    // Função auxiliar para tratar a lógica de repetição de jogadas
    private static void jogarRodada(Jogador jogador) {
        int jogadasNaRodada = 0;
        int posicaoInicial = jogador.getPosicao();

        // Usamos loop para controlar jogadas extras (caso dados sejam iguais)
        boolean repetir;
        do {
            repetir = false;
            jogador.jogar();
            jogadasNaRodada++;

            int d1 = jogador.getDados()[0];
            int d2 = jogador.getDados()[1];

            System.out.println("  Jogada " + jogadasNaRodada + ": dados = [" + d1 + ", " + d2 + "], soma = " + (d1 + d2));
            System.out.println("  Posição atual: " + jogador.getPosicao());

            if (d1 == d2) {
                System.out.println("  🎲 Dados iguais! " + jogador.getCor() + " joga novamente!");
                repetir = true;
            }

        } while (repetir);

        System.out.println("  -> " + jogador.getCor() + " terminou a rodada na casa " + jogador.getPosicao()
                + " (andou " + (jogador.getPosicao() - posicaoInicial) + " casas nesta rodada)");
    }
}
