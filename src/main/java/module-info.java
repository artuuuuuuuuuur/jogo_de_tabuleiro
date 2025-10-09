module com.uece.poo.jogo_de_tabuleiro {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.uece.poo.jogo_de_tabuleiro to javafx.fxml;
    exports com.uece.poo.jogo_de_tabuleiro;
}
