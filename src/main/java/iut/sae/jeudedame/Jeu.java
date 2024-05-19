package iut.sae.jeudedame;

import iut.sae.jeudedame.objets.Partie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Jeu extends Application {
    private Partie partie; // Instance de la partie de jeu de dames

    @Override
    public void start(Stage stage) throws IOException {
        // Chargement de l'interface utilisateur depuis le fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Jeu.class.getResource("jeu-dame.fxml"));
        GridPane root = fxmlLoader.load();

        // Création de la scène et affichage de l'interface utilisateur
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Jeu de Dames");
        stage.setScene(scene);
        stage.show();

        // Initialisation de la partie de jeu de dames
        partie = new Partie();
        partie.initialisation();
    }

    public static void main(String[] args) {
        launch();
    }
}
