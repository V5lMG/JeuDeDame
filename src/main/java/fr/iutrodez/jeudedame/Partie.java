package fr.iutrodez.jeudedame;

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

    public Plateau getPlateau() {
        return plateau;
    }

    public void selectionnerPion(int x, int y) {
        out.println("Sélection du pion à (" + x + ", " + y + ")...");
        Pion pion = joueurActuel.getPionAt(x, y);
        if (pion != null) {
            pionSelectionne = pion;
            out.println("Pion sélectionné.");
            return;
        }
        out.println("Aucun pion à sélectionner.");
    }

    public boolean deplacerPion(int newX, int newY) {
        out.println("Déplacement du pion vers (" + newX + ", " + newY + ")...");
        if (pionSelectionne == null) {
            out.println("Erreur : Aucun pion n'a été sélectionné.");
            return false;  // Empêche le déplacement si aucun pion n'est sélectionné
        }
        if (plateau.estDeplacementValide(pionSelectionne, newX, newY)) {
            plateau.deplacerPion(pionSelectionne, newX, newY);
            out.println("Pion déplacé avec succès.");
            pionSelectionne = null;
            return true;
        }
        out.println("Déplacement invalide : Partie.java");
        return false;
    }
}
