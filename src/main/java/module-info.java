module iut.sae.jeudedame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens iut.sae.jeudedame to javafx.fxml;
    exports iut.sae.jeudedame;
}