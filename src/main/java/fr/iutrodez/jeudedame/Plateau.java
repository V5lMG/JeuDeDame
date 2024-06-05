package fr.iutrodez.jeudedame;

import static java.lang.System.out;

public class Plateau {
    private Pion[][] cases = new Pion[10][10];

    public Pion[][] getCases() {
        return cases;
    }

    public void initialiser(Joueur joueurNoir, Joueur joueurBlanc) {
        out.println("Initialisation du plateau...");

        String blackImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_noir.png";
        String whiteImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_blanc.png";

        initialiserPions(joueurNoir, 0, 3, "NOIR", blackImageUrl);
        initialiserPions(joueurBlanc, 6, 9, "BLANC", whiteImageUrl);

        out.println("Plateau initialisé.");
    }

    private void initialiserPions(Joueur joueur, int startRow, int endRow, String color, String imagePath) {
        out.println("Initialisation des pions pour le joueur " + color + "...");
        for (int y = startRow; y <= endRow; y++) {
            for (int x = (y % 2 == 0 ? 1 : 0); x < 10; x += 2) {
                Pion pion = new Pion(false, x, y, color, imagePath);
                joueur.ajouterPion(pion);
                placerPion(pion, x, y);
            }
        }
        out.println("Pions initialisés pour le joueur " + color + ".");
    }

    public void placerPion(Pion pion, int x, int y) {
        out.println("Placement du pion à (" + x + ", " + y + ")...");
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            cases[y][x] = pion;
            out.println("Pion placé à (" + x + ", " + y + ") avec la couleur " + pion.getColor());
        } else {
            out.println("Tentative de placement du pion hors limites à (" + x + ", " + y + ").");
        }
    }

    public Pion getPion(int x, int y) {
        out.println("Obtention du pion à (" + x + ", " + y + ")...");
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            return cases[y][x];
        }
        out.println("Aucun pion à (" + x + ", " + y + ").");
        return null;
    }

    public boolean estDeplacementValide(Pion pion, int newX, int newY) {
        // Hors des limites ou case occupée
        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10 || cases[newY][newX] != null) {
            out.println("hors du plateau");
            return false;
        }

        return true;
    }

    public void deplacerPion(Pion pion, int newX, int newY) {
        if (!estDeplacementValide(pion, newX, newY)) {
            System.out.println("Déplacement invalide vers (" + newX + ", " + newY + ").");
            return; // Déplacement n'est pas valide, donc ne faites rien.
        }

        int oldX = pion.getPosX();
        int oldY = pion.getPosY();

        // Retirer le pion de sa position actuelle
        cases[oldY][oldX] = null;

        // Si le déplacement est un saut, cela implique une capture
        if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 2) {
            int midX = (newX + oldX) / 2;
            int midY = (newY + oldY) / 2;
            Pion capturedPion = cases[midY][midX];
            if (capturedPion != null && !capturedPion.getColor().equals(pion.getColor())) {
                cases[midY][midX] = null; // Retirer le pion capturé
                System.out.println("Pion capturé à (" + midX + ", " + midY + ").");
            }
        }

        // Déplacer le pion à la nouvelle position
        pion.setPosX(newX);
        pion.setPosY(newY);
        cases[newY][newX] = pion;

        System.out.println("Pion déplacé de (" + oldX + ", " + oldY + ") à (" + newX + ", " + newY + ").");
    }
}
