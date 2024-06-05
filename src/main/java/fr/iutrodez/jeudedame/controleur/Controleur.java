package fr.iutrodez.jeudedame.controleur;

import fr.iutrodez.jeudedame.modele.Partie;
import fr.iutrodez.jeudedame.modele.Pion;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Controleur {
    @FXML public GridPane gameGrid;
    @FXML private List<Pion> captureReadyPions = new ArrayList<>();
    private Partie partie;
    private ImageView selectedPionImageView;
    private Pion selectedPion;

    @FXML
    public void initialize() {
        out.println("Initialisation du contrôleur...");
        partie = new Partie();
        drawInitialBoard();
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
        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);
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

            out.println("Cas cliqué sur (" + x + ", " + y + ").");

            if (selectedPion != null && !captureReadyPions.isEmpty()) {
                if (!captureReadyPions.contains(selectedPion)) {
                    out.println("Le pion sélectionné ne peut pas capturer.");
                    return;
                }
            }

            Pion pionOnCase = partie.getPionAt(x, y);
            if (pionOnCase != null) {
                out.println("Il y a déjà un pion sur cette case.");
                return;
            }

            if (selectedPion != null) {
                out.println("Tentative de déplacer le pion sélectionné...");
                attemptMove(x, y);
                removeAllHighlight();
            }
        } catch (Exception e) {
            out.println("Cliquez sur la grille de traitement des erreurs : " + e.getMessage());
        }
    }

    private void onPionClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        int x = GridPane.getColumnIndex(clickedImageView);
        int y = GridPane.getRowIndex(clickedImageView);
        Pion clickedPion = partie.getPionAt(x, y);

        updateCaptureReadyPions();

        if (clickedPion != null && clickedPion.getColor().equals(partie.getJoueurActuel().getColor())) {
            if (!captureReadyPions.isEmpty()) {
                if (captureReadyPions.contains(clickedPion)) {
                    selectPion(clickedImageView, clickedPion);
                    highlightLegalMoves(clickedPion);
                } else {
                    out.println("Ce pion ne peut pas bouger maintenant. Veuillez sélectionner un pion qui peut capturer.");
                }
            } else {
                selectPion(clickedImageView, clickedPion);
                highlightLegalMoves(clickedPion);
            }
        }
    }

    private void updateCaptureReadyPions() {
        captureReadyPions.clear();
        for (Pion pion : partie.getAllPions(partie.getJoueurActuel().getColor())) {
            if (partie.canCapture(pion)) {
                captureReadyPions.add(pion);
            }
        }
        highlightPionsForCapture();
        highlightMovesForAllCaptureReadyPions();
    }

    private void highlightMovesForAllCaptureReadyPions() {
        for (Pion pion : captureReadyPions) {
            highlightLegalMoves(pion);
        }
    }

    private void selectPion(ImageView imageView, Pion pion) {
        out.println("Sélection du pion à (" + pion.getPosX() + ", " + pion.getPosY() + ")...");
        selectedPion = pion;
        selectedPionImageView = imageView;
        partie.selectionnerPion(pion.getPosX(), pion.getPosY());
        highlightPion(imageView);
        out.println("Pion sélectionné et mouvements possibles affichés.");
    }

    private void highlightLegalMoves(Pion pion) {
        removeAllHighlight();
        List<int[]> legalMoves = partie.getPlateau().getLegalMoves(pion);
        System.out.println("Mouvement legal trouvé : " + legalMoves.size());
        for (int[] move : legalMoves) {
            int x = move[0];
            int y = move[1];
            System.out.println("Essayer de surligner le mouvement à: " + x + ", " + y);
            Node node = findNodeByGridPosition(x, y);
            if (node != null) {
                boolean isCaptureMove = Math.abs(x - pion.getPosX()) == 2 && Math.abs(y - pion.getPosY()) == 2;
                String cssClass = "highlight-move";
                node.getStyleClass().add(cssClass);
                System.out.println("Highlighted: " + cssClass);

                if (isCaptureMove) {
                    int midX = (pion.getPosX() + x) / 2;
                    int midY = (pion.getPosY() + y) / 2;
                    ImageView capturePionImageView = findPionImageViewAt(midX, midY);
                    if (capturePionImageView != null) {
                        capturePionImageView.getStyleClass().add("highlight-capture");
                        System.out.println("Highlighted capture à: [" + midX + ", " + midY + "]");
                    } else {
                        System.out.println("ImageView introuvable pour la capture à [" + midX + ", " + midY + "]");
                    }
                }
            } else {
                System.out.println("Node introuvable sur [" + x + ", " + y + "]");
            }
        }
    }

    private void highlightPionsForCapture() {
        for (Pion pion : captureReadyPions) {
            ImageView pionView = findPionImageViewAt(pion.getPosX(), pion.getPosY());
            if (pionView != null) {
                highlightPion(pionView);
            }
        }
    }

    private ImageView findPionImageViewAt(int x, int y) {
        for (Node node : gameGrid.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return (ImageView) node;
            }
        }
        return null;
    }

    private Node findNodeByGridPosition(int x, int y) {
        for (Node node : gameGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == x &&
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == y) {
                return node;
            }
        }
        return null;
    }

    private void attemptMove(int newX, int newY) {
        int oldX = selectedPion.getPosX();
        int oldY = selectedPion.getPosY();

        if (partie.deplacerPion(newX, newY)) {
            movePion(selectedPionImageView, newX, newY);
            updateCaseOnMove(oldX, oldY, newX, newY);
            partie.changerJoueur();
            out.println("Pion déplacé avec succès.");
        } else {
            out.println("Tentative de déplacement invalide.");
        }
    }

    private void updateCaseOnMove(int oldX, int oldY, int newX, int newY) {
        partie.getPlateau().getCase(oldX, oldY).reset();
        partie.getPlateau().getCase(newX, newY).setPion(selectedPion);

        if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 2) {
            int midX = (oldX + newX) / 2;
            int midY = (oldY + newY) / 2;
            Pion capturedPion = partie.getPionBetween(oldX, oldY, newX, newY);
            if (capturedPion != null) {
                partie.getPlateau().getCase(midX, midY).reset();
                removePionFromView(capturedPion);
                partie.capturerPion(capturedPion);
                out.println("Pion capturé et retiré du plateau.");
            }
        }
    }

    public void removePionFromView(Pion capturedPion) {
        for (Node node : gameGrid.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == capturedPion.getPosX() && GridPane.getRowIndex(node) == capturedPion.getPosY()) {
                gameGrid.getChildren().remove(node);
                out.println("Pion ImageView retiré du GUI à (" + capturedPion.getPosX() + ", " + capturedPion.getPosY() + ")");
                break;
            }
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
    }

    public void setPartie(Partie partie) {
        out.println("Définir une nouvelle partie...");
        this.partie = partie;
        drawInitialBoard();
        out.println("Nouvelle partie définie.");
    }

    private void removeAllHighlight() {
        for (Node node : gameGrid.getChildren()) {
            node.getStyleClass().removeAll("highlighted-pion", "highlight-move", "highlight-capture", "highlight-capture-move", "highlighted-case");
            node.setStyle("");
        }
    }
}