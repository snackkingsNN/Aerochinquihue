module com.aerochinquihue {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    opens com.aerochinquihue to javafx.fxml;
    opens com.aerochinquihue.model to javafx.base;
    opens com.aerochinquihue.controller to javafx.fxml;

    exports com.aerochinquihue;
    exports com.aerochinquihue.controller;
    exports com.aerochinquihue.model;
}
