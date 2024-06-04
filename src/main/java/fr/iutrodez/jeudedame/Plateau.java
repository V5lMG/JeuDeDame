package fr.iutrodez.jeudedame;

public class Plateau {
    private Pion[][] cases = new Pion[10][10];

    public Pion[][] getCases() {
        return cases;
    }

    public void initialiser(Joueur joueurNoir, Joueur joueurBlanc) {
        System.out.println("Initialisation du plateau...");

        String blackImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_noir.png";
        String whiteImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/pion_blanc.png";

        initialiserPions(joueurNoir, 0, 3, "NOIR", blackImageUrl);
        initialiserPions(joueurBlanc, 6, 9, "BLANC", whiteImageUrl);

        System.out.println("Plateau initialisé.");
    }

    private void initialiserPions(Joueur joueur, int startRow, int endRow, String color, String imagePath) {
        System.out.println("Initialisation des pions pour le joueur " + color + "...");
        for (int y = startRow; y <= endRow; y++) {
            for (int x = (y % 2 == 0 ? 1 : 0); x < 10; x += 2) {
                Pion pion = new Pion(false, x, y, color, imagePath);
                joueur.ajouterPion(pion);
                placerPion(pion, x, y);
            }
        }
        System.out.println("Pions initialisés pour le joueur " + color + ".");
    }

    public void placerPion(Pion pion, int x, int y) {
        System.out.println("Placement du pion à (" + x + ", " + y + ")...");
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            cases[y][x] = pion;
            System.out.println("Pion placé à (" + x + ", " + y + ") avec la couleur " + pion.getColor());
        } else {
            System.out.println("Tentative de placement du pion hors limites à (" + x + ", " + y + ").");
        }
    }

    public Pion getPion(int x, int y) {
        System.out.println("Obtention du pion à (" + x + ", " + y + ")...");
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            return cases[y][x];
        }
        System.out.println("Aucun pion à (" + x + ", " + y + ").");
        return null;
    }

    public boolean estDeplacementValide(Pion pion, int newX, int newY) {
        // Hors des limites ou case occupée
        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10 || cases[newY][newX] != null) {
            return false;
        }

        int deltaX = Math.abs(newX - pion.getPosX());
        int deltaY = Math.abs(newY - pion.getPosY());

        // Vérifier les mouvements standards pour pions et dames
        if ((deltaX == 1 && deltaY == 1) || (deltaX == 2 && deltaY == 2)) {
            if (deltaX == 2) {  // tentative de capture
                int midX = (newX + pion.getPosX()) / 2;
                int midY = (newY + pion.getPosY()) / 2;
                Pion pionMilieu = cases[midY][midX];
                if (pionMilieu != null && !pionMilieu.getColor().equals(pion.getColor())) {
                    return true;  // capture valide
                }
                return false;  // pas de pion à capturer
            }
            return true;  // déplacement simple valide
        }
        return false;  // déplacement non diagonal ou invalide
    }

    public void deplacerPion(Pion pion, int newX, int newY) {
        if (estDeplacementValide(pion, newX, newY)) {
            int oldX = pion.getPosX();
            int oldY = pion.getPosY();
            cases[oldY][oldX] = null;  // Enlever de l'ancienne position

            if (Math.abs(newX - oldX) == 2) {
                int midX = (newX + oldX) / 2;
                int midY = (newY + oldY) / 2;
                cases[midY][midX] = null;  // Capture
            }

            pion.setPosX(newX);
            pion.setPosY(newY);
            cases[newY][newX] = pion;  // Placer à la nouvelle position
        }
    }
}
