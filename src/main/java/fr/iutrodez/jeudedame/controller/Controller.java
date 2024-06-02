package fr.iutrodez.jeudedame.controller;

import fr.iutrodez.jeudedame.modele.Joueur;
import fr.iutrodez.jeudedame.modele.Pion;
import fr.iutrodez.jeudedame.modele.Plateau;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private GridPane gameGrid;

    private Plateau plateau;
    private Joueur joueurNoir;
    private Joueur joueurBlanc;

    @FXML
    public void initialize() {
        System.out.println("Initialisation du contrôleur...");
        if (gameGrid == null) {
            System.out.println("Erreur: gameGrid est null");
        } else {
            System.out.println("gameGrid est initialisé");
        }

        plateau = new Plateau();
        joueurNoir = new Joueur("Noir");
        joueurBlanc = new Joueur("Blanc");
        plateau.initialiser(joueurNoir, joueurBlanc);
        for (Pion[] row : plateau.getCases()) {
            for (Pion pion : row) {
                if (pion != null) {
                    placePionInView(pion, pion.getPosX(), pion.getPosY());
                }
            }
        }
    }

    private void placePionInView(Pion pion, int x, int y) {
        ImageView imageView = new ImageView(pion.getImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gameGrid.add(imageView, x, y);

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);

        System.out.println("ImageView ajoutée au GridPane en [" + x + "," + y + "]");
    }
}

