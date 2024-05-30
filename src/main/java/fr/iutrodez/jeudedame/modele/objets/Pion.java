package fr.iutrodez.jeudedame.modele.objets;

public class Pion {
    private String couleur;
    private boolean estDame;
    private int[] position;
    private String etat;

    public Pion(String couleur, int[] position) {
        this.couleur = couleur;
        this.estDame = false;
        this.position = position;
        this.etat = "vie";
    }

    public String getCouleurPion() {
        return couleur;
    }

    public boolean estDame() {
        return estDame;
    }

    public int[] getPositionPion() {
        return position;
    }

    public String getEtatPion() {
        return etat;
    }

    public void promouvoir() {
        estDame = true;
    }

    public void setCouleurPion(String couleur) {
        this.couleur = couleur;
    }

    public void setPositionPion(int[] position) {
        this.position = position;
    }

    public void setEtatPion(String etat) {
        this.etat = etat;
    }
}
