package fr.iutrodez.jeudedame.modele;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Jeu extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Chargement de l'interface utilisateur depuis le fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/iutrodez/jeudedame/vue/jeu-dame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Configuration de la fenêtre principale
        stage.setTitle("Jeu de Dames");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
