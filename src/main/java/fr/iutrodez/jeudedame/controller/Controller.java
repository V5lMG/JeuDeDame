package fr.iutrodez.jeudedame.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;

public class Controller {
    @FXML
    private GridPane plateau;

    @FXML
    public void initialize() {
        setupGameBoard();
    }

    private void setupGameBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-border-width: 1;");
                if ((row + col) % 2 != 0) {  // Assuming black cells have pawns
                    if (row < 3) {
                        addPion(cell, "pion_blanc.png");
                    } else if (row > 4) {
                        addPion(cell, "pion_noir.png");
                    }
                }
                plateau.add(cell, col, row);
            }
        }
    }

    private void addPion(StackPane cell, String imageName) {
        try {

            File imageFile = new File("src/main/java/fr/iutrodez/jeudedame/vue/" + imageName);
            URL imageUrl = imageFile.toURI().toURL();

            ImageView imageView = new ImageView(new Image(String.valueOf(imageUrl)));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            cell.getChildren().add(imageView);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image: " + imageName + " - " + e.getMessage());
        }
    }
}
