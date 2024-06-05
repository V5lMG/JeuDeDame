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
    }

    public void ajouterPion(Pion pion) {
        pions.add(pion);
    }

    public void retirerPion(Pion pion) {
        if (pions.remove(pion)) {
            out.println("Pion supprimé du joueur " + this.color);
        } else {
            out.println("Erreur: Pion non trouvé pour suppression.");
        }
    }

    public List<Pion> getPions() {
        return pions;
    }

    public String getColor()     {
        return color;
    }
}
