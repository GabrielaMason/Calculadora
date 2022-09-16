module com.example.calcmiddleware {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calcmiddleware to javafx.fxml;
    exports com.example.calcmiddleware;
}