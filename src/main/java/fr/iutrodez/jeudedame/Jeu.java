package fr.iutrodez.jeudedame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Jeu extends Application {

    public static void main(String[] args) {
        System.out.println("Démarrage du jeu de dames...");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        File fxmlFile = new File("src/main/java/fr/iutrodez/jeudedame/vue/jeu-dame.fxml");
        URL fxmlUrl = fxmlFile.toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);

        if (fxmlLoader.getLocation() == null) {
            throw new IOException("URL du fxml invalide");
        }
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Jeu de Dames");
        stage.setScene(scene);
        stage.show();

        // Après l'affichage, simulez une action ou log pour indiquer que le jeu commence
        System.out.println("La partie commence !");
    }
}
