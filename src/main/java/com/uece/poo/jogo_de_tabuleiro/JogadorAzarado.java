package com.uece.poo.jogo_de_tabuleiro;

public class JogadorAzarado extends Jogador {
  public JogadorAzarado(String cor) {
    super(cor);
  }
  
  @Override
  public void jogar() {
    jogarNovamente = true;
    while (jogarNovamente) {
      jogarNovamente = false;
      rolarDados();
      if (dados[0] + dados[1] <= 6) {
        andar(dados[0] + dados[1]);
        vezesJogadas++;
      } else {
        jogarNovamente = true;
      }
      if (dados[0] == dados[1]) {
        jogarNovamente = true;
      }
    }
  }

}
