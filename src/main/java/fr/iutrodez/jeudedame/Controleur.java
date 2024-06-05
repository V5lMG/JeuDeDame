package fr.iutrodez.jeudedame;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static java.lang.System.out;

public class Controleur {
    @FXML public GridPane gameGrid;
    private Partie partie;
    private ImageView selectedPionImageView;
    private Pion selectedPion;

    @FXML
    public void Controleur() {
        out.println("Initialisation du contrôleur...");
        partie = new Partie();
        drawInitialBoard();
        out.println("Contrôleur initialisé.");
    }

    private void drawInitialBoard() {
        out.println("Dessiner le plateau initial...");
        gameGrid.getChildren().clear();

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Pane cell = new Pane();
                cell.setOnMouseClicked(this::onCaseClicked);
                GridPane.setConstraints(cell, x, y);
                GridPane.setRowIndex(cell, y);
                GridPane.setColumnIndex(cell, x);
                gameGrid.getChildren().add(cell);
            }
        }

        for (Pion pion : partie.getJoueurNoir().getPions()) {
            placePionInView(pion, pion.getPosX(), pion.getPosY());
        }
        for (Pion pion : partie.getJoueurBlanc().getPions()) {
            placePionInView(pion, pion.getPosX(), pion.getPosY());
        }
        out.println("Plateau initial dessiné.");
    }

    private void placePionInView(Pion pion, int x, int y) {
        ImageView imageView = new ImageView(pion.getImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setSmooth(true);
        imageView.setOnMouseClicked(this::onPionClicked);
        GridPane.setConstraints(imageView, x, y);
        gameGrid.add(imageView, x, y);
    }

    @FXML
    private void onCaseClicked(MouseEvent event) {
        try {
            Node source = (Node) event.getSource();
            Integer x = GridPane.getColumnIndex(source);
            Integer y = GridPane.getRowIndex(source);

            if (x == null || y == null) {
                return;
            }

            out.println("Case cliquée à (" + x + ", " + y + ").");

            if (selectedPion != null && partie.hasCaptureMove(partie.getJoueurActuel())) {
                if (!partie.canCapture(selectedPion)) {
                    out.println("Le pion sélectionné ne peut pas capturer.");
                    highlightPionsForCapture();
                    return;
                }
            }

            Pion pionSurCase = partie.getPionAt(x, y);
            if (pionSurCase != null) {
                out.println("Il y a déjà un pion sur cette case.");
                return;
            }

            if (selectedPion != null) {
                out.println("Tentative de déplacement du pion sélectionné...");
                attemptMove(x, y);
            }
        } catch (Exception e) {
            out.println("Erreur lors du traitement du clic sur la grille: " + e.getMessage());
        }
    }

    private void highlightPionsForCapture() {
        for (Pion pion : partie.getJoueurActuel().getPions()) {
            if (partie.canCapture(pion) && pion != selectedPion) {
                int x = pion.getPosX();
                int y = pion.getPosY();
                for (Node node : gameGrid.getChildren()) {
                    if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node instanceof ImageView) {
                        highlightPion((ImageView) node);
                    }
                }
            }
        }
    }

    private void onPionClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        int x = GridPane.getColumnIndex(clickedImageView);
        int y = GridPane.getRowIndex(clickedImageView);
        out.println("Pion cliqué à (" + x + ", " + y + ").");

        Pion clickedPion = partie.getPionAt(x, y);
        if (clickedPion != null && clickedPion.getColor().equals(partie.getJoueurActuel().getColor())) {
            if (partie.hasCaptureMove(partie.getJoueurActuel()) && !partie.canCapture(clickedPion)) {
                out.println("Le pion sélectionné ne peut pas capturer.");
                highlightPionsForCapture();
                return; // Ne rien faire si le pion ne peut pas capturer
            }
            if (selectedPionImageView != null) {
                deselectPion();
            }
            selectPion(clickedImageView, clickedPion);
        }
    }

    private void selectPion(ImageView imageView, Pion pion) {
        out.println("Sélection du pion à (" + pion.getPosX() + ", " + pion.getPosY() + ")...");
        selectedPion = pion;
        selectedPionImageView = imageView;
        partie.selectionnerPion(pion.getPosX(), pion.getPosY());
        highlightPion(imageView);
    }

    private void attemptMove(int newX, int newY) {
        out.println("Tentative de déplacement du pion vers (" + newX + ", " + newY + ")...");

        int oldX = selectedPion.getPosX();
        int oldY = selectedPion.getPosY();

        if (partie.deplacerPion(newX, newY)) {
            movePion(selectedPionImageView, newX, newY);

            if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 2) {
                Pion capturedPion = partie.getPionBetween(oldX, oldY, newX, newY);
                if (capturedPion != null) {
                    removePionFromView(capturedPion);
                    partie.capturerPion(capturedPion);
                    out.println("Pion capturé et retiré du plateau.");
                }
            }

            deselectPion();
            partie.changerJoueur();
            out.println("Pion déplacé avec succès.");
        } else {
            out.println("Tentative de déplacement invalide.");
        }
    }

    public void removePionFromView(Pion capturedPion) {
        Node nodeToRemove = null;
        out.println("Tentative de suppression du pion en vue à (" + capturedPion.getPosX() + ", " + capturedPion.getPosY() + ")...");
        for (Node node : gameGrid.getChildren()) {
            if (node instanceof ImageView) {
                Integer xPane = GridPane.getColumnIndex(node);
                Integer yPane = GridPane.getRowIndex(node);
                out.println("Vérification du Node à (" + xPane + ", " + yPane + ")...");
                if (xPane != null && yPane != null && xPane == capturedPion.getPosX() && yPane == capturedPion.getPosY()) {
                    nodeToRemove = node;
                    break;
                }
            }
        }
        if (nodeToRemove != null) {
            gameGrid.getChildren().remove(nodeToRemove);
            out.println("Pion retiré de la vue.");
        } else {
            out.println("Pas de Node à supprimer pour les coordonnées (" + capturedPion.getPosX() + ", " + capturedPion.getPosY() + ").");
        }
    }

    private void movePion(ImageView pion, int newX, int newY) {
        out.println("Déplacement du pion en vue vers (" + newX + ", " + newY + ")...");
        GridPane.setColumnIndex(pion, newX);
        GridPane.setRowIndex(pion, newY);
        out.println("Pion déplacé en vue.");
    }

    private void highlightPion(ImageView pion) {
        out.println("Surligner le pion...");
        pion.getStyleClass().add("highlighted-pion");
        out.println("Pion surligné.");
    }

    private void deselectPion() {
        out.println("Désélection du pion...");
        if (selectedPionImageView != null) {
            selectedPionImageView.getStyleClass().remove("highlighted-pion");
            selectedPionImageView = null;
            selectedPion = null;
        }
        out.println("Pion désélectionné.");
    }

    public void setPartie(Partie partie) {
        out.println("Définir une nouvelle partie...");
        this.partie = partie;
        drawInitialBoard();
        out.println("Nouvelle partie définie.");
    }
}
