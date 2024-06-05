package fr.iutrodez.jeudedame;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Joueur {
    private List<Pion> pions;  // Liste des pions contrôlés par le joueur
    private String color; // Couleur du joueur

    public Joueur(String color) {
        out.println("Création du joueur avec la couleur : " + color);
        this.color = color;
        this.pions = new ArrayList<>();
        out.println("Joueur créé.");
    }

    // Ajoute un pion à la collection de pions du joueur
    public void ajouterPion(Pion pion) {
        out.println("Ajout d'un pion au joueur...");
        pions.add(pion);
        out.println("Pion ajouté.");
    }

    public void retirerPion(Pion pion) {
        out.println("Suppression d'un pion du joueur...");
        pions.remove(pion);
        out.println("Pion supprimé.");
    }

    // Obtient la liste des pions
    public List<Pion> getPions() {
        return pions;
    }

    public Pion getPionAt(int x, int y) {
        out.println("Obtention du pion à (" + x + ", " + y + ")...");
        for (Pion pion : pions) {
            if (pion.getPosX() == x && pion.getPosY() == y) {
                out.println("Pion trouvé à (" + x + ", " + y + ").");
                return pion;
            }
        }
        out.println("Aucun pion trouvé à (" + x + ", " + y + ").");
        return null; // Retourne null si aucun pion n'est trouvé aux coordonnées données
    }

    public String getColor() {
        return color;
    }
}
