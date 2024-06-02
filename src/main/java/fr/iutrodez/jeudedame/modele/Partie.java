package fr.iutrodez.jeudedame.modele.objets;

public class Partie {
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Plateau plateau;

    public Partie() {
        this.joueurBlanc = new Joueur("Blanc");
        this.joueurNoir = new Joueur("Noir");
        this.plateau = new Plateau();
    }

    public void initialisation() {
        plateau.setTheme("Thème personnalisé"); // TODO : Enlever le STUB pour les thèmes

        initialiserPions(joueurBlanc);
        initialiserPions(joueurNoir);
    }

    private void initialiserPions(Joueur joueur) {
        for (int i = 0; i < 3; i++) {
            for (int j = i % 2; j < 8; j += 2) {
                Pion pion = new Pion(joueur.getCouleur(), new int[]{i, j});
                joueur.getPionsEnJeu().add(pion);
            }
        }
    }

}
