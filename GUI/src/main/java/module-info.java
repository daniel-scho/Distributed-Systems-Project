module com.fh.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.apache.pdfbox;
    requires javafx.web;
    requires java.desktop;


    opens com.fh.gui to javafx.fxml;
    exports com.fh.gui;
}