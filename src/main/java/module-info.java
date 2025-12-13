module com.uece.poo.jogo_de_tabuleiro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.uece.poo.jogo_de_tabuleiro.controller to javafx.fxml;
    exports com.uece.poo.jogo_de_tabuleiro.controller;
    exports com.uece.poo.jogo_de_tabuleiro;
    opens com.uece.poo.jogo_de_tabuleiro to javafx.fxml;
}
