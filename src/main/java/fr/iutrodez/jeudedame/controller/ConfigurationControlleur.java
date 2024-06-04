package fr.iutrodez.jeudedame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class ConfigurationController {

    @FXML
    private CheckBox pionNoirCheckBox;
    @FXML
    private CheckBox pionBlancCheckBox;
    @FXML
    private CheckBox plateauBoisCheckBox;
    @FXML
    private CheckBox plateauClassiqueCheckBox;
    @FXML
    private CheckBox fondBoisCheckBox;
    @FXML
    private CheckBox fondGrisCheckBox;

    @FXML
    private void handlePionColorSelection() {
        if (pionNoirCheckBox.isSelected()) {
            pionBlancCheckBox.setSelected(false);
        } else if (pionBlancCheckBox.isSelected()) {
            pionNoirCheckBox.setSelected(false);
        }
    }

    @FXML
    private void handlePlateauSelection() {
        if (plateauBoisCheckBox.isSelected()) {
            plateauClassiqueCheckBox.setSelected(false);
        } else if (plateauClassiqueCheckBox.isSelected()) {
            plateauBoisCheckBox.setSelected(false);
        }
    }

    @FXML
    private void handleBackgroundSelection() {
        if (fondBoisCheckBox.isSelected()) {
            fondGrisCheckBox.setSelected(false);
        } else if (fondGrisCheckBox.isSelected()) {
            fondBoisCheckBox.setSelected(false);
        }
    }
}
