package fr.iutrodez.jeudedame.controleur;

import fr.iutrodez.jeudedame.modele.Partie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.lang.System.out;

public class AccueilControleur {

    @FXML private HBox start;
    @FXML private HBox load;
    @FXML private HBox quit;

    public HBox getStartHBox() {
        return start;
    }

    public HBox getQuitHBox() {
        return quit;
    }

    @FXML
    public void initialize() {
        System.out.println("AccueilControleur initialized");

        start.setOnMouseClicked(event -> {
            System.out.println("Nouvelle Partie HBox clicked");
            try {
                lancerJeu((Stage) start.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        load.setOnMouseClicked(event -> {
            System.out.println("Charger une Partie HBox clicked");
        });

        quit.setOnMouseClicked(event -> {
            System.out.println("Quitter le Jeu HBox clicked");
            Stage stage = (Stage) quit.getScene().getWindow();
            stage.close();
        });
    }

    public void lancerJeu(Stage stage) throws IOException {
        Partie partie = new Partie();
        File fxmlFile = new File("src/main/java/fr/iutrodez/jeudedame/vue/plateau/jeu-dame.fxml");
        URL plateauUrl = fxmlFile.toURI().toURL();
        if (plateauUrl == null) {
            throw new IOException("Le fichier FXML est introuvable");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(plateauUrl);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Jeu de Dames");

        Controleur controller = fxmlLoader.getController();
        controller.setPartie(partie);
        controller.initialize();

        stage.show();

        out.println("La partie commence !");
    }
}
