package com.uece.poo.jogo_de_tabuleiro;

public class JogadorNormal extends Jogador {
  public JogadorNormal(String cor) {
    super(cor);
  }
  
  @Override
  public void jogar() {
    jogarNovamente = true;
    while (jogarNovamente) { 
      jogarNovamente = false;
      
      rolarDados();
      andar(dados[0] + dados[1]);
      vezesJogadas++;
      
    
      if(dados[0] == dados[1]){
        jogarNovamente = true;
      } 
    }
    
  }

}
