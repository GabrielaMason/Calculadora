module com.example.calcserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calcserver to javafx.fxml;
    exports com.example.calcserver;
}