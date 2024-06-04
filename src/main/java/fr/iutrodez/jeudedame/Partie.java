package fr.iutrodez.jeudedame;

public class Partie {
    private Joueur joueurNoir;
    private Joueur joueurBlanc;
    private Joueur joueurActuel;
    private Pion pionSelectionne;
    private Plateau plateau;

    public Partie() {
        System.out.println("Démarrage d'une nouvelle partie...");
        joueurNoir = new Joueur("NOIR");
        joueurBlanc = new Joueur("BLANC");
        joueurActuel = joueurNoir;
        plateau = new Plateau();
        plateau.initialiser(joueurNoir, joueurBlanc); // Noir commence
        System.out.println("Nouvelle partie démarrée.");
    }

    public void changerJoueur() {
        System.out.println("Changement de joueur...");
        joueurActuel = (joueurActuel == joueurNoir) ? joueurBlanc : joueurNoir;
        System.out.println("C'est maintenant au tour du joueur " + joueurActuel.getColor() + ".");
    }

    public Joueur getJoueurBlanc() {
        return joueurBlanc;
    }

    public Pion getPionAt(int x, int y) {
        System.out.println("Obtention du pion à (" + x + ", " + y + ") depuis le plateau...");
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

    public boolean selectionnerPion(int x, int y) {
        System.out.println("Sélection du pion à (" + x + ", " + y + ")...");
        Pion pion = joueurActuel.getPionAt(x, y);
        if (pion != null) {
            pionSelectionne = pion;
            System.out.println("Pion sélectionné.");
            return true;
        }
        System.out.println("Aucun pion à sélectionner.");
        return false;
    }

    public boolean deplacerPion(int newX, int newY) {
        System.out.println("Déplacement du pion vers (" + newX + ", " + newY + ")...");
        if (pionSelectionne != null && plateau.estDeplacementValide(pionSelectionne, newX, newY)) {
            plateau.deplacerPion(pionSelectionne, newX, newY);
            pionSelectionne = null;
            changerJoueur();
            System.out.println("Pion déplacé avec succès.");
            return true;
        }
        System.out.println("Déplacement invalide.");
        return false;
    }
}
