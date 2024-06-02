package fr.iutrodez.jeudedame.modele.objets;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private String couleur;
    private List<Pion> pionsEnJeu;

    public Joueur(String couleur) {
        this.couleur = couleur;
        this.pionsEnJeu = new ArrayList<>(); // Initialisation de la liste des pièces
    }

    public String getCouleur() {
        return couleur;
    }

    public List<Pion> getPionsEnJeu() {
        return pionsEnJeu;
    }

    public void setPieces(List<Pion> pieces) {
        this.pionsEnJeu = pieces;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getNombrePionsEnJeu(Joueur joueur) {
        return pionsEnJeu.size();
    }
}
