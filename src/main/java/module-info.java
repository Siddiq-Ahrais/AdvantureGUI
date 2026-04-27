module Advanture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens pack to javafx.fxml;
    exports pack;
}
