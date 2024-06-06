package fr.iutrodez.jeudedame.modele;

import javafx.application.Platform;

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
        if (!isWithinBounds(newX, newY)) {
            return false;
        }
        int dx = newX - pion.getPosX();
        int dy = newY - pion.getPosY();
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);
        if (absDx != absDy) {
            return false;
        }
        if (cases[newY][newX].estOccupee()) {
            return false;
        }
        if (absDx == 1) {
            return pion.isDame() || (pion.getColor().equals("NOIR") && dy == -1) || (pion.getColor().equals("BLANC") && dy == 1);
        } else if (absDx == 2) {
            int midX = pion.getPosX() + dx / 2;
            int midY = pion.getPosY() + dy / 2;
            Pion midPion = cases[midY][midX].getPion();
            return midPion != null && !midPion.getColor().equals(pion.getColor());
        }

        return false;
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

        // Vérification de la fin de la partie
        Joueur gagnant = verifierFinDePartie();
        if (gagnant != null) {
            out.println("La partie est terminée. Le gagnant est " + gagnant.getColor());
            stopChronometer(true);
        }
    }

    public boolean stopChronometer(boolean stopSignal) {
        return stopSignal;
    }

    public void verifierPromotion(Pion pion) {
        if ((pion.getColor().equals("NOIR") && pion.getPosY() == 9) || (pion.getColor().equals("BLANC") && pion.getPosY() == 0)) {
            pion.promouvoir();
        }
    }

    private Joueur verifierFinDePartie() {
        if (joueurNoir.getNombreDePions() == 0) {
            return joueurBlanc;  // Joueur blanc a gagné
        } else if (joueurBlanc.getNombreDePions() == 0) {
            return joueurNoir;  // Joueur noir a gagné
        }
        return null;  // La partie continue
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
