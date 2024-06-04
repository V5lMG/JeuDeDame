package fr.iutrodez.jeudedame.modele;

import javafx.scene.paint.Color;

public class Plateau {
    private Pion[][] cases;

    public Plateau() {
        cases = new Pion[10][10];
    }

    public void initialiser(Joueur joueurNoir, Joueur joueurBlanc) {
        System.out.println("Initialisation du plateau...");

        String blackImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_noir.png";
        String whiteImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_blanc.png";

        initialiserPions(joueurNoir, 0, 3, Color.BLACK, blackImageUrl);
        initialiserPions(joueurBlanc, 6, 9, Color.WHITE, whiteImageUrl);
    }

    private void initialiserPions(Joueur joueur, int ligneDebut, int ligneFin, Color color, String imageUrl) {
        for (int y = ligneDebut; y <= ligneFin; y++) {
            for (int x = 0; x < cases[y].length; x++) {
                if ((x + y) % 2 != 0) { // Les pions sont placés sur les cases noires uniquement
                    Pion pion = new Pion(false, x, y, color, imageUrl);
                    cases[y][x] = pion;
                    joueur.ajouterPion(pion);
                }
            }
        }
    }

    public Pion getPion(int x, int y) {
        return cases[y][x];
    }

    public boolean deplacerPion(int xSource, int ySource, int xDest, int yDest) {
        Pion pion = getPion(xSource, ySource);
        if (pion != null && estDeplacementValide(pion, xDest, yDest)) {
            retirerPion(pion); // Retirer le pion de sa position actuelle
            pion.setPosX(xDest);
            pion.setPosY(yDest);
            placerPion(pion, xDest, yDest); // Placer le pion à la nouvelle position
            return true;
        }
        return false;
    }

    private boolean estDeplacementValide(Pion pion, int xDest, int yDest) {
        // Vérifiez que le déplacement est dans les limites du plateau
        if (xDest < 0 || xDest >= 10 || yDest < 0 || yDest >= 10) {
            return false;
        }

        // Vérifiez que la case de destination est vide
        if (getPion(xDest, yDest) != null) {
            return false;
        }

        // Règles de déplacement pour un pion
        int dx = xDest - pion.getPosX();
        int dy = yDest - pion.getPosY();

        if (!pion.isDame()) {
            // Déplacement en diagonale d'une case (pion simple)
            if (Math.abs(dx) == 1 && Math.abs(dy) == 1) {
                return true;
            }

            // Prise d'un pion en sautant par-dessus (pion simple)
            if (Math.abs(dx) == 2 && Math.abs(dy) == 2) {
                int xMid = pion.getPosX() + dx / 2;
                int yMid = pion.getPosY() + dy / 2;
                Pion pionIntermediaire = getPion(xMid, yMid);
                if (pionIntermediaire != null && pionIntermediaire.getColor() != pion.getColor()) {
                    retirerPion(pionIntermediaire); // Retirer le pion capturé
                    return true;
                }
            }
        } else {
            // Règles de déplacement pour une dame (à compléter)
        }

        return false;
    }

    private void retirerPion(Pion pion) {
        cases[pion.getPosY()][pion.getPosX()] = null;
    }

    private void placerPion(Pion pion, int x, int y) {
        cases[y][x] = pion;
    }

    public Pion[][] getCases() {
        return cases;
    }
}
