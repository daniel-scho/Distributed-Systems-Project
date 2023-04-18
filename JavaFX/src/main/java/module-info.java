module com.fh.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens com.fh.javafx to javafx.fxml;
    exports com.fh.javafx;
}