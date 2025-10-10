package com.uece.poo.jogo_de_tabuleiro;

public class JogadorComSorte extends Jogador {
  public JogadorComSorte(String cor) {
    super(cor);
  }
  
  @Override
  public void jogar() {
    jogarNovamente = true;
    while(jogarNovamente) {
      jogarNovamente = false;
      rolarDados();
      
      if(dados[0] + dados[1] >= 7) {
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
