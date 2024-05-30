package fr.iutrodez.jeudedame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Controller {
    @FXML
    private GridPane gameGrid;

    @FXML
    public void initialize() {
        // Initialiser le GridPane avec des cases
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-border-width: 1;");
                final int r = row, c = col;  // Variables finales pour l'utilisation dans la lambda
                cell.setOnMouseClicked(e -> colorCell(cell));
                gameGrid.add(cell, col, row);
            }
        }
    }

    private void colorCell(StackPane cell) {
        cell.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-width: 1;");
    }
}
