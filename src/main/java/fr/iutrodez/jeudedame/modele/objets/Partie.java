package fr.iutrodez.jeudedame.modele;

public class Partie {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Plateau plateau;
    private Joueur joueurActuel;

    public Partie() {
        this.joueurBlanc = new Joueur("Blanc");
        this.joueurNoir = new Joueur("Noir");
        this.plateau = new Plateau();
        this.plateau.initialiser(joueurNoir, joueurBlanc); // Initialiser le plateau avec les pions des joueurs
        this.joueurActuel = joueurBlanc; // Le joueur blanc commence
    }
    
    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public void changerTour() {
        joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
    }

    public Joueur getJoueurBlanc() {
        return joueurBlanc;
    }

    public Joueur getJoueurNoir() {
        return joueurNoir;
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
