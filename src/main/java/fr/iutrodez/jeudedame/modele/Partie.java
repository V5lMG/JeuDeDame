package fr.iutrodez.jeudedame.modele;

public class Partie {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Plateau plateau;

    public Partie() {
        this.joueurBlanc = new Joueur("Blanc");
        this.joueurNoir = new Joueur("Noir");
        this.plateau = new Plateau();
    }
}
