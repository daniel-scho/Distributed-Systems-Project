module com.fh.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com.fh.gui to javafx.fxml;
    exports com.fh.gui;
}