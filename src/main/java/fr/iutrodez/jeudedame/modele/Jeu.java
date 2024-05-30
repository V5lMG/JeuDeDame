package fr.iutrodez.jeudedame.modele;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Jeu extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        File fxmlFile = new File("src/main/java/fr/iutrodez/jeudedame/vue/jeu-dame.fxml");
        URL fxmlUrl = fxmlFile.toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);


        if (fxmlLoader.getLocation() == null) {
            throw new IOException("Cannot load resource: jeu-dame.fxml");
        }
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);

        // Configuration de la fenêtre principale
        stage.setTitle("Jeu de Dames");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
