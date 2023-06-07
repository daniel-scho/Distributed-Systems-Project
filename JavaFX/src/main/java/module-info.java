module com.fh.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;
    opens com.fh.javafx to javafx.fxml;
    exports com.fh.javafx;
}
