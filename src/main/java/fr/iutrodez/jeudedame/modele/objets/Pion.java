package fr.iutrodez.jeudedame.modele;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static java.lang.System.out;

public class Pion {
    private boolean isDame;
    private int posX;
    private int posY;
    private Color color;
    private Image image;

    public Pion(boolean isDame, int posX, int posY, Color color, String imagePath) {
        this.isDame = isDame;
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        try {
            File imageFile = new File(imagePath);
            URL imageUrl = imageFile.toURI().toURL();
            this.image = new Image(imageUrl.openStream());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + imagePath + "\n ERREUR : " + e.getMessage());

        }
        out.println("Création d'un pion en [" + posX + "," + posY + "] isDame: " + isDame);
    }

    public Image getImage() {
        return image;
    }

    public boolean isDame() {
        return isDame;
    }

    public void setDame(boolean dame) {
        isDame = dame;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
