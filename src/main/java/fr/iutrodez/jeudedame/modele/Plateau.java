package fr.iutrodez.jeudedame.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;

public class Plateau {
    private Case[][] cases = new Case[10][10];
    private Joueur joueurNoir;
    private Joueur joueurBlanc;

    public Plateau() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cases[y][x] = new Case(x, y);
            }
        }
    }

    public void initialiser(Joueur joueurNoir, Joueur joueurBlanc) {
        out.println("Initialisation du plateau...");
        this.joueurNoir = joueurNoir;
        this.joueurBlanc = joueurBlanc;

        String blackImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/image/pion_noir.png";
        String whiteImageUrl = "src/main/java/fr/iutrodez/jeudedame/vue/image/pion_blanc.png";


        initialiserPions(joueurBlanc, 0, 3, "BLANC", whiteImageUrl);
        initialiserPions(joueurNoir, 6, 9, "NOIR", blackImageUrl);

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
        if (isWithinBounds(x, y)) {
            cases[y][x].setPion(pion);
            out.println("Pion placé à (" + x + ", " + y + ")");
        } else {
            out.println("Tentative de placement du pion hors limites à (" + x + ", " + y + ")");
        }
    }

    public Pion getPion(int x, int y) {
        if (isWithinBounds(x, y)) {
            return cases[y][x].getPion();
        }
        return null;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public boolean estDeplacementValide(Pion pion, int newX, int newY) {
        if (!isWithinBounds(newX, newY) || cases[newY][newX].estOccupee()) {
            return false;
        }

        int dx = Math.abs(newX - pion.getPosX());
        int dy = Math.abs(newY - pion.getPosY());

        if (!pion.isDame()) {
            if ((pion.getColor().equals("NOIR") && newY > pion.getPosY()) ||
                    (pion.getColor().equals("BLANC") && newY < pion.getPosY())) {
                out.println("Déplacement invalide : les pions ne peuvent pas reculer.");
                return false;
            }
        }

        if (pion.isDame()) {
            return dx == dy && cheminLibre(pion.getPosX(), pion.getPosY(), newX, newY);
        } else {
            return (dx == 1 && dy == 1) || (dx == 2 && dy == 2 && caseIntermediaireContientAdversaire(pion, newX, newY));
        }
    }

    private boolean cheminLibre(int startX, int startY, int endX, int endY) {
        int dx = Integer.signum(endX - startX);
        int dy = Integer.signum(endY - startY);
        int distance = Math.abs(endX - startX);
        for (int step = 1; step < distance; step++) {
            int x = startX + step * dx;
            int y = startY + step * dy;
            if (cases[y][x].estOccupee()) {
                return false;
            }
        }
        return true;
    }

    private boolean caseIntermediaireContientAdversaire(Pion pion, int newX, int newY) {
        int midX = (pion.getPosX() + newX) / 2;
        int midY = (pion.getPosY() + newY) / 2;
        Pion midPion = getPion(midX, midY);
        return midPion != null && !midPion.getColor().equals(pion.getColor());
    }

    public void supprimerPion(Pion pion) {
        cases[pion.getPosY()][pion.getPosX()].setPion(null);
        out.println("Pion supprimé du modèle");
    }

    public void deplacerPion(Pion pion, int newX, int newY) {
        if (!estDeplacementValide(pion, newX, newY)) {
            out.println("Déplacement invalide.");
            return;
        }

        cases[pion.getPosY()][pion.getPosX()].setPion(null);
        pion.setPosX(newX);
        pion.setPosY(newY);
        cases[newY][newX].setPion(pion);

        if (Math.abs(newX - pion.getPosX()) == 2) {
            int midX = (pion.getPosX() + newX) / 2;
            int midY = (pion.getPosY() + newY) / 2;
            Pion capturedPion = getPion(midX, midY);
            if (capturedPion != null) {
                cases[midY][midX].setPion(null);
                Joueur opponent = capturedPion.getColor().equals("NOIR") ? joueurBlanc : joueurNoir;
                opponent.retirerPion(capturedPion);
                out.println("Pion capturé à (" + midX + ", " + midY + ")");
            }
        }

        verifierPromotion(pion);
        out.println("Pion déplacé de (" + pion.getPosX() + ", " + pion.getPosY() + ") à (" + newX + ", " + newY + ")");
    }

    public void verifierPromotion(Pion pion) {
        if ((pion.getColor().equals("NOIR") && pion.getPosY() == 9) || (pion.getColor().equals("BLANC") && pion.getPosY() == 0)) {
            pion.promouvoir();
        }
    }

    public List<int[]> getLegalMoves(Pion pion) {
        List<int[]> moves = new ArrayList<>();
        List<int[]> captureMoves = new ArrayList<>();
        int x = pion.getPosX();
        int y = pion.getPosY();

        int[][] directions = pion.isDame()
                ? new int[][]{{-1, -1}, {1, -1}, {-1, 1}, {1, 1}}
                : pion.getColor().equals("NOIR")
                ? new int[][]{{-1, -1}, {1, -1}}
                : new int[][]{{-1, 1}, {1, 1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            int jumpX = x + 2 * dir[0];
            int jumpY = y + 2 * dir[1];

            if (isWithinBounds(newX, newY) && !cases[newY][newX].estOccupee()) {
                moves.add(new int[]{newX, newY});
            }

            if (isWithinBounds(jumpX, jumpY) && isWithinBounds(newX, newY) &&
                    cases[newY][newX].estOccupee() && !cases[jumpY][jumpX].estOccupee() &&
                    !Objects.equals(cases[newY][newX].getPion().getColor(), pion.getColor())) {
                captureMoves.add(new int[]{jumpX, jumpY});
            }
        }

        return captureMoves.isEmpty() ? moves : captureMoves;
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= cases.length || y < 0 || y >= cases[x].length) {
            return null;
        }
        return cases[y][x];
    }
}
