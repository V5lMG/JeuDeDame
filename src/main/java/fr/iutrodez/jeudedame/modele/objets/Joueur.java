package fr.iutrodez.jeudedame.modele;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private List<Pion> pions;
    private String nom;

    public Joueur(String nom) {
        this.nom = nom;
        this.pions = new ArrayList<>();
    }

    public void ajouterPion(Pion pion) {
        pions.add(pion);
    }

    public List<Pion> getPions() {
        return pions;
    }

    public String getNom() {
        return nom;
    }
}
