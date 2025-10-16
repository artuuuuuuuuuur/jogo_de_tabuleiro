package com.uece.poo.jogo_de_tabuleiro;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void switchToChoosePlayer() throws IOException {
        App.setRoot("choose_player");
    }
}
