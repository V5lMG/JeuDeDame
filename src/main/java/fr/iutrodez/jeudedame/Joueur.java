package fr.iutrodez.jeudedame;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private List<Pion> pions;  // Liste des pions contrôlés par le joueur
    private String color;  // Couleur du joueur

    // Constructeur initialise la couleur du joueur et la liste des pions
    public Joueur(String color) {
        System.out.println("Création du joueur avec la couleur : " + color);
        this.color = color;
        this.pions = new ArrayList<>();
        System.out.println("Joueur créé.");
    }

    // Ajoute un pion à la collection de pions du joueur
    public void ajouterPion(Pion pion) {
        System.out.println("Ajout d'un pion au joueur...");
        pions.add(pion);
        System.out.println("Pion ajouté.");
    }

    // Retire un pion de la liste, utilisé si un pion est capturé
    public void retirerPion(Pion pion) {
        System.out.println("Suppression d'un pion du joueur...");
        pions.remove(pion);
        System.out.println("Pion supprimé.");
    }

    // Obtient la liste des pions
    public List<Pion> getPions() {
        return pions;
    }

    public Pion getPionAt(int x, int y) {
        System.out.println("Obtention du pion à (" + x + ", " + y + ")...");
        for (Pion pion : pions) {
            if (pion.getPosX() == x && pion.getPosY() == y) {
                System.out.println("Pion trouvé à (" + x + ", " + y + ").");
                return pion;
            }
        }
        System.out.println("Aucun pion trouvé à (" + x + ", " + y + ").");
        return null; // Retourne null si aucun pion n'est trouvé aux coordonnées données
    }

    // Obtient la couleur du joueur
    public String getColor() {
        return color;
    }

    // Méthode pour vérifier si un pion spécifique appartient au joueur
    public boolean estProprietaireDuPion(Pion pion) {
        System.out.println("Vérification si le joueur possède le pion...");
        boolean result = pions.contains(pion);
        System.out.println("Résultat de la vérification de propriété : " + result);
        return result;
    }
}
