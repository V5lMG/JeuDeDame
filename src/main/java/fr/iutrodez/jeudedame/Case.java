package fr.iutrodez.jeudedame;

public class Case {
    private int x;
    private int y;
    private Pion pion;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.pion = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pion getPion() {
        return pion;
    }

    public void setPion(Pion pion) {
        this.pion = pion;
    }

    public boolean estOccupee() {
        return pion != null;
    }
}
