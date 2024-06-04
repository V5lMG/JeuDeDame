package fr.iutrodez.jeudedame;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML private GridPane gameGrid;
    private Partie partie;
    private ImageView selectedPionImageView;
    private Pion selectedPion;

    @FXML
    public void initialize() {
        System.out.println("Initialisation du contrôleur...");
        partie = new Partie();
        drawInitialBoard();
        System.out.println("Contrôleur initialisé.");
    }

    private void drawInitialBoard() {
        System.out.println("Dessiner le plateau initial...");
        gameGrid.getChildren().clear();

        for (Pion pion : partie.getJoueurNoir().getPions()) {
            placePionInView(pion, pion.getPosX(), pion.getPosY());
        }
        for (Pion pion : partie.getJoueurBlanc().getPions()) {
            placePionInView(pion, pion.getPosX(), pion.getPosY());
        }
        System.out.println("Plateau initial dessiné.");
    }

    private void placePionInView(Pion pion, int x, int y) {
        ImageView imageView = new ImageView(pion.getImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setOnMouseClicked(this::onPionClicked);
        GridPane.setConstraints(imageView, x, y);
        gameGrid.add(imageView, x, y);
    }

    @FXML
    private void onGridClicked(MouseEvent event) {
        int x = (int) (event.getX() / 50);
        int y = (int) (event.getY() / 50);
        System.out.println("Case cliquée à (" + x + ", " + y + ").");

        if (selectedPion != null) {
            System.out.println("Tentative de déplacement du pion sélectionné...");
            attemptMove(x, y);
        }
    }

    private void onPionClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        int x = GridPane.getColumnIndex(clickedImageView);
        int y = GridPane.getRowIndex(clickedImageView);
        System.out.println("Pion cliqué à (" + x + ", " + y + ").");

        Pion clickedPion = partie.getPionAt(x, y);
        if (clickedPion != null && clickedPion.getColor().equals(partie.getJoueurActuel().getColor())) {
            System.out.println("Sélection du pion...");
            if (selectedPionImageView != null) {
                deselectPion();
            }
            selectPion(clickedImageView, clickedPion);
            System.out.println("Pion sélectionné.");
        }
    }

    private void selectPion(ImageView imageView, Pion pion) {
        System.out.println("Sélection du pion à (" + pion.getPosX() + ", " + pion.getPosY() + ")...");
        selectedPion = pion;
        selectedPionImageView = imageView;
        highlightPion(imageView);
        System.out.println("Pion sélectionné.");
    }

    private void attemptMove(int newX, int newY) {
        System.out.println("Tentative de déplacement du pion vers (" + newX + ", " + newY + ")...");
        if (partie.deplacerPion(newX, newY)) {
            movePion(selectedPionImageView, newX, newY);
            deselectPion();
            partie.changerJoueur();
            System.out.println("Pion déplacé avec succès.");
        } else {
            System.out.println("Tentative de déplacement invalide.");
        }
    }

    private void movePion(ImageView pion, int newX, int newY) {
        System.out.println("Déplacement du pion en vue vers (" + newX + ", " + newY + ")...");
        GridPane.setColumnIndex(pion, newX);
        GridPane.setRowIndex(pion, newY);
        System.out.println("Pion déplacé en vue.");
    }

    private void highlightPion(ImageView pion) {
        System.out.println("Surligner le pion...");
        pion.getStyleClass().add("highlighted-pion");
        System.out.println("Pion surligné.");
    }

    private void deselectPion() {
        System.out.println("Désélection du pion...");
        if (selectedPionImageView != null) {
            selectedPionImageView.getStyleClass().remove("highlighted-pion");
            selectedPionImageView = null;
            selectedPion = null;
        }
        System.out.println("Pion désélectionné.");
    }

    public void setPartie(Partie partie) {
        System.out.println("Définir une nouvelle partie...");
        this.partie = partie;
        drawInitialBoard();
        System.out.println("Nouvelle partie définie.");
    }
}
