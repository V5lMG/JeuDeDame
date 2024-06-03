package fr.iutrodez.jeudedame.controller;

import fr.iutrodez.jeudedame.modele.Joueur;
import fr.iutrodez.jeudedame.modele.Pion;
import fr.iutrodez.jeudedame.modele.Plateau;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller {
	
    @FXML
    private GridPane gameGrid;
    
    // Déclaration de la variable partie
    private Partie partie;
    private Plateau plateau;
    private Joueur joueurNoir;
    private Joueur joueurBlanc;
    private Pion pionSelectionne;
    private int xSource, ySource;
    
    public void initialize(Partie partie) {
        this.partie = partie;
    }
    
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
        afficherPlateau();
    }

    private void afficherPlateau() {
        gameGrid.getChildren().clear();
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
        imageView.setOnMouseClicked(this::handlePionClick);
        gameGrid.add(imageView, x, y);

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);

        System.out.println("ImageView ajoutée au GridPane en [" + x + "," + y + "]");
    }

    private void handlePionClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Integer x = GridPane.getColumnIndex(imageView);
        Integer y = GridPane.getRowIndex(imageView);

        if (pionSelectionne == null) {
            pionSelectionne = plateau.getPion(x, y);
            xSource = x;
            ySource = y;
            System.out.println("Pion sélectionné en [" + x + "," + y + "]");
            imageView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0, 0, 0);"); // Ajouter une surbrillance
        } else {
            if (plateau.deplacerPion(xSource, ySource, x, y)) {
                System.out.println("Pion déplacé de [" + xSource + "," + ySource + "] à [" + x + "," + y + "]");
                pionSelectionne = null;
                afficherPlateau();
                partie.changerTour(); // Changer de tour après un mouvement valide
                verifierConditionVictoire(); // Vérifier la condition de victoire
            } else {
                System.out.println("Déplacement invalide");
                pionSelectionne = null;
            }
        }
    }
    private void verifierConditionVictoire() {
        if (joueurBlanc.getPions().isEmpty()) {
            System.out.println("Le joueur Noir a gagné !");
        } else if (joueurNoir.getPions().isEmpty()) {
            System.out.println("Le joueur Blanc a gagné !");
        }
    }
}
