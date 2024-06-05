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
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        Partie partie = new Partie();
        File fxmlFile = new File("src/main/java/fr/iutrodez/jeudedame/vue/plateau/jeu-dame.fxml");
        URL fxmlUrl = fxmlFile.toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);

        if (fxmlLoader.getLocation() == null) {
            throw new IOException("URL du fxml invalide");
        }

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Jeu de Dames");
        stage.show();

        Controleur controller = fxmlLoader.getController();
        controller.setPartie(partie);
        controller.initialize();

        System.out.println("La partie commence !");
    }
}
