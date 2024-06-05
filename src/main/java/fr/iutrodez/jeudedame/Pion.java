package fr.iutrodez.jeudedame;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;

public class Pion {
    private boolean isDame;
    private int posX;
    private int posY;
    private String color;
    private Image image;

    public Pion(boolean isDame, int posX, int posY, String color, String imagePath) {
        System.out.println("Création du pion à (" + posX + ", " + posY + ") avec la couleur " + color + "...");
        this.isDame = isDame;
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        loadImage(imagePath);
        System.out.println("Pion créé.");
    }

    private void loadImage(String imagePath) {
        System.out.println("Chargement de l'image pour le pion...");
        try {
            File imageFile = new File(imagePath);
            URL imageUrl = imageFile.toURI().toURL();
            this.image = new Image(imageUrl.openStream());
            System.out.println("Image chargée avec succès pour le pion en [" + posX + "," + posY + "]");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + imagePath);
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }

    public boolean isDame() {
        return isDame;
    }

    public void setDame(boolean dame) {
        System.out.println("Définir le statut de dame du pion à " + dame + "...");
        isDame = dame;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        System.out.println("Définir la position X du pion à " + posX + "...");
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        System.out.println("Définir la position Y du pion à " + posY + "...");
        this.posY = posY;
    }

    public String getColor() {
        return color;
    }

    // Méthode pour promouvoir le pion en dame
    public void promouvoir() {
        if (!isDame) {
            if ((color.equals("NOIR") && posY == 9) || (color.equals("BLANC") && posY == 0)) {
                setDame(true);
                System.out.println("Pion promu en dame en position [" + posX + "," + posY + "]");
            }
        }
    }
}
