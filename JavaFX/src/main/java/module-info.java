module com.fh.javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fh.javafx to javafx.fxml;
    exports com.fh.javafx;
}