package fr.iutrodez.jeudedame.modele;

import javafx.scene.paint.Color;

public class Plateau {
    private Pion[][] cases = new Pion[10][10];

    public Pion[][] getCases() {
        return cases;
    }

    public void initialiser(Joueur joueurNoir, Joueur joueurBlanc) {
        System.out.println("Initialisation du plateau...");

        String blackImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_noir.png";
        String whiteImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_blanc.png";

        initialiserPions(joueurNoir, 0, 3, Color.BLACK, blackImageUrl);
        initialiserPions(joueurBlanc, 6, 9, Color.WHITE, whiteImageUrl);
    }

    private void initialiserPions(Joueur joueur, int startRow, int endRow, Color color, String imagePath) {
        for (int y = startRow; y <= endRow; y++) {
            for (int x = (y % 2 == 0 ? 1 : 0); x < 10; x += 2) {
                Pion pion = new Pion(false, x, y, color, imagePath);
                joueur.ajouterPion(pion);
                placerPion(pion, x, y);
            }
        }
    }

    public void placerPion(Pion pion, int x, int y) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            cases[y][x] = pion;
            System.out.println("Pion placé en [" + x + "," + y + "] avec couleur " + pion.getColor());
        } else {
            System.out.println("Tentative de placement d'un pion hors limites en [" + x + "," + y + "]");
        }
    }

    public Pion getPion(int x, int y) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            return cases[y][x];
        }
        return null;
    }
    public boolean deplacerPion(int xSource, int ySource, int xDest, int yDest) {
        Pion pion = getPion(xSource, ySource);
        if (pion != null && estDeplacementValide(pion, xDest, yDest)) {
            placerPion(pion, xDest, yDest);
            cases[ySource][xSource] = null;
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
                    return true;
                }
            }
        } else {
            // Règles de déplacement pour une dame (déplacement en diagonale multiple)
            if (Math.abs(dx) == Math.abs(dy)) {
                int stepX = dx / Math.abs(dx);
                int stepY = dy / Math.abs(dy);

                for (int i = 1; i < Math.abs(dx); i++) {
                    if (getPion(pion.getPosX() + i * stepX, pion.getPosY() + i * stepY) != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    private void retirerPion(Pion pion, Joueur joueurNoir, Joueur joueurBlanc) {
        cases[pion.getPosY()][pion.getPosX()] = null;
        if (pion.getColor() == Color.BLACK) {
            joueurNoir.getPions().remove(pion);
        } else {
            joueurBlanc.getPions().remove(pion);
        }
        System.out.println("Pion retiré en [" + pion.getPosX() + "," + pion.getPosY() + "]");
    }
}
