module iut.sae.jeudedame {
    requires javafx.controls;
    requires javafx.fxml;

    exports fr.iutrodez.jeudedame.controller;
    opens fr.iutrodez.jeudedame.controller to javafx.fxml;
    exports fr.iutrodez.jeudedame.modele;
    opens fr.iutrodez.jeudedame.modele to javafx.fxml;
    exports fr.iutrodez.jeudedame.modele.objets;
    opens fr.iutrodez.jeudedame.modele.objets to javafx.fxml;
}
