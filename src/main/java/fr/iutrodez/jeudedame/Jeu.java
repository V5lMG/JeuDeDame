package fr.iutrodez.jeudedame;

import fr.iutrodez.jeudedame.controleur.AccueilControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.lang.System.out;

public class Jeu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL accueilUrl = getClass().getResource("/fr/iutrodez/jeudedame/vue/accueil/page-accueil.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(accueilUrl);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Jeu de Dames");
        stage.show();

        AccueilControleur accueilControleur = fxmlLoader.getController();

        accueilControleur.getStartHBox().setOnMouseClicked(event -> {
            try {
                accueilControleur.lancerJeu(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        accueilControleur.getQuitHBox().setOnMouseClicked(event -> {
            stage.close();
        });

        out.println("L'application est lancée");
    }
}
