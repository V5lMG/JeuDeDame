package fr.iutrodez.jeudedame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class Controller {

    @FXML
    private Label welcomeText;

    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private void initialize() {
        // Initialization
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onQuitAction() {
        // Logic to quit the application, assumes you have linked this method to a MenuItem
        System.exit(0);
    }
}
