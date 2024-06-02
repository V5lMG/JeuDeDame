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
}
