module com.example.calcclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calcclient to javafx.fxml;
    exports com.example.calcclient;
}