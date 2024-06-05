package fr.iutrodez.jeudedame;

import static java.lang.System.out;

public class Plateau {
    private Case[][] cases = new Case[10][10];
    private Joueur joueurNoir;
    private Joueur joueurBlanc;

    public void initialiser(Joueur joueurNoir, Joueur joueurBlanc) {
        out.println("Initialisation du plateau...");

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cases[y][x] = new Case(x, y);
            }
        }

        String blackImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/image/pion_noir.png";
        String whiteImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/image/pion_blanc.png";

        initialiserPions(joueurNoir, 0, 3, "NOIR", blackImageUrl);
        initialiserPions(joueurBlanc, 6, 9, "BLANC", whiteImageUrl);

        this.joueurNoir = joueurNoir;
        this.joueurBlanc = joueurBlanc;

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
            cases[y][x].setPion(pion);
            out.println("Pion placé à (" + x + ", " + y + ") avec la couleur " + pion.getColor());
        } else {
            out.println("Tentative de placement du pion hors limites à (" + x + ", " + y + ").");
        }
    }

    public Pion getPion(int x, int y) {
        out.println("Obtention du pion à (" + x + ", " + y + ")...");
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            return cases[y][x].getPion();
        }
        out.println("Aucun pion à (" + x + ", " + y + ").");
        return null;
    }

    public boolean estDeplacementValide(Pion pion, int newX, int newY) {
        int oldX = pion.getPosX();
        int oldY = pion.getPosY();
        int dx = newX - oldX;
        int dy = newY - oldY;

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
            out.println("Déplacement hors du plateau.");
            return false;
        }

        if (cases[newY][newX].estOccupee()) {
            out.println("La case de destination n'est pas libre.");
            return false;
        }

        if (pion.isDame()) {
            if (Math.abs(dx) == Math.abs(dy) && cheminLibre(oldX, oldY, dx, dy)) {
                return true;
            }
        } else {
            if (Math.abs(dx) == 2 && Math.abs(dy) == 2) {
                // Capture possible
                if (caseIntermediaireContientAdversaire(oldX, oldY, dx, dy, pion.getColor())) {
                    return true;
                }
            }

            // Déplacement normal
            if (Math.abs(dx) == 1 && Math.abs(dy) == 1 && ((pion.getColor().equals("NOIR") && dy == 1) || (pion.getColor().equals("BLANC") && dy == -1))) {
                return true;
            }
        }

        out.println("Déplacement non valide selon les règles.");
        return false;
    }

    private boolean cheminLibre(int oldX, int oldY, int dx, int dy) {
        int stepX = Integer.signum(dx);
        int stepY = Integer.signum(dy);
        int steps = Math.abs(dx);
        for (int i = 1; i < steps; i++) {
            if (cases[oldY + i * stepY][oldX + i * stepX].estOccupee()) {
                return false;
            }
        }
        return true;
    }

    private boolean caseIntermediaireContientAdversaire(int oldX, int oldY, int dx, int dy, String pionColor) {
        int midX = oldX + dx / 2;
        int midY = oldY + dy / 2;

        Pion midPion = getPion(midX, midY);
        if (midPion != null && !midPion.getColor().equals(pionColor)) {
            out.println("Pion adversaire trouvé à (" + midX + ", " + midY + ") : " + midPion);
            return true;
        }

        out.println("Aucun pion adversaire à (" + midX + ", " + midY + ")");
        return false;
    }

    public void deplacerPion(Pion pion, int newX, int newY) {
        if (!estDeplacementValide(pion, newX, newY)) {
            out.println("Déplacement invalide vers (" + newX + ", " + newY + ").");
            return;
        }

        int oldX = pion.getPosX();
        int oldY = pion.getPosY();

        cases[oldY][oldX].setPion(null);

        if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 2) {
            // Calculer la position intermédiaire
            int midX = (newX + oldX) / 2;
            int midY = (newY + oldY) / 2;
            Pion capturedPion = cases[midY][midX].getPion();
            if (capturedPion != null && !capturedPion.getColor().equals(pion.getColor())) {
                cases[midY][midX].setPion(null);
                Joueur adversaire = pion.getColor().equals("NOIR") ? joueurBlanc : joueurNoir;
                adversaire.retirerPion(capturedPion);
                out.println("Pion capturé à (" + midX + ", " + midY + ").");
            }
        }

        pion.setPosX(newX);
        pion.setPosY(newY);
        cases[newY][newX].setPion(pion);
        verifierPromotion(pion);
        out.println("Pion déplacé de (" + oldX + ", " + oldY + ") à (" + newX + ", " + newY + ").");
    }

    public void verifierPromotion(Pion pion) {
        if ((pion.getColor().equals("NOIR") && pion.getPosY() == 9) ||
                (pion.getColor().equals("BLANC") && pion.getPosY() == 0)) {
            pion.promouvoir();
        }
    }
}
