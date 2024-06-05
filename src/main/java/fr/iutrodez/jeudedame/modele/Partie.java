package fr.iutrodez.jeudedame.modele;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Partie {
    private Joueur joueurNoir;
    private Joueur joueurBlanc;
    private Joueur joueurActuel;
    private Pion pionSelectionne;
    private Plateau plateau;

    public Partie() {
        out.println("Démarrage d'une nouvelle partie...");
        joueurNoir = new Joueur("NOIR");
        joueurBlanc = new Joueur("BLANC");
        joueurActuel = joueurNoir;
        plateau = new Plateau();
        plateau.initialiser(joueurNoir, joueurBlanc); // Noir commence
        out.println("Nouvelle partie démarrée.");
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void changerJoueur() {
        out.println("Changement de joueur...");
        joueurActuel = (joueurActuel == joueurNoir) ? joueurBlanc : joueurNoir;
        out.println("C'est maintenant au tour du joueur " + joueurActuel.getColor() + ".");
    }

    public Joueur getJoueurBlanc() {
        return joueurBlanc;
    }

    public Pion getPionAt(int x, int y) {
        out.println("Obtention du pion à (" + x + ", " + y + ") depuis le plateau...");
        return plateau.getPion(x, y);
    }

    public Joueur getJoueurNoir() {
        return joueurNoir;
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public void selectionnerPion(int x, int y) {
        out.println("Sélection du pion à (" + x + ", " + y + ")...");
        Pion pion = getPionAt(x, y);
        if (pion != null && pion.getColor().equals(joueurActuel.getColor())) {
            pionSelectionne = pion;
            out.println("Pion sélectionné.");
            return;
        }
        out.println("Aucun pion à sélectionner.");
    }

    public boolean deplacerPion(int x, int y) {
        if (pionSelectionne == null || !isWithinBounds(x, y)) {
            return false;
        }
        if (plateau.estDeplacementValide(pionSelectionne, x, y)) {
            int oldX = pionSelectionne.getPosX();
            int oldY = pionSelectionne.getPosY();
            plateau.deplacerPion(pionSelectionne, x, y);

            if (Math.abs(oldX - x) == 2 && Math.abs(oldY - y) == 2) {
                Pion pionCapture = getPionBetween(oldX, oldY, x, y);
                if (pionCapture != null) {
                    capturerPion(pionCapture);
                }
            }

            pionSelectionne = null;
            return true;
        }
        return false;
    }

    public boolean canCapture(Pion pion) {
        int x = pion.getPosX();
        int y = pion.getPosY();

        int[][] directions = {{-2, -2}, {-2, 2}, {2, -2}, {2, 2}};
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            int midX = x + direction[0] / 2;
            int midY = y + direction[1] / 2;

            if (isWithinBounds(newX, newY) && plateau.getPion(newX, newY) == null &&
                    plateau.getPion(midX, midY) != null && !plateau.getPion(midX, midY).getColor().equals(pion.getColor())) {
                return true;
            }
        }
        return false;
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public void capturerPion(Pion pion) {
        Joueur joueur = pion.getColor().equals("NOIR") ? getJoueurNoir() : getJoueurBlanc();
        joueur.retirerPion(pion);
    }

    public Pion getPionBetween(int startX, int startY, int endX, int endY) {
        int midX = (startX + endX) / 2;
        int midY = (startY + endY) / 2;
        return getPionAt(midX, midY);
    }

    public Pion[] getAllPions(String color) {
        List<Pion> pions = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Pion pion = plateau.getPion(x, y);
                if (pion != null && pion.getColor().equals(color)) {
                    pions.add(pion);
                }
            }
        }
        return pions.toArray(new Pion[0]); // Return as an array of Pions
    }
}
