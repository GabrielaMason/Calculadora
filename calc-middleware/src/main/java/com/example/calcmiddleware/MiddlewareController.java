package com.example.calcmiddleware;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class MiddlewareController implements Initializable {

    @FXML
    private ScrollPane sp_main;

    @FXML
    private VBox vbox_operations;

    private Middleware middleware;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            middleware = new Middleware(new ServerSocket(1234));
            middleware.startMiddleware(vbox_operations);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating Middleware instance");
        }

        vbox_operations.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sp_main.setVvalue((Double) t1);
            }
        });

    }

    public static void addMessageLog(DataComponent data, VBox vbox) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.setSpacing(10);

        VBox lvBox = new VBox();
        lvBox.setAlignment(Pos.CENTER_LEFT);
        lvBox.setSpacing(2);

        String operator = data.num1;
        switch (data.operator) {
            case "1":
                operator += " + ";
                break;
            case "2":
                operator += " - ";
                break;
            case "3":
                operator += " * ";
                break;
            default:
                operator += " / ";
                break;
        }
        operator += data.num2;
        if (!data.result.equals(" ")) {
            operator += " = " + data.result;
        }

        Text dataBody = new Text(operator);
        Separator separator = new Separator(Orientation.HORIZONTAL);

        dataBody.setStyle("-fx-font: 12 arial;");

        TextFlow txtFlowO = new TextFlow(dataBody);

        lvBox.getChildren().add(txtFlowO);
        lvBox.setPadding(new Insets(5, 10, 5, 10));

        hBox.getChildren().add(lvBox);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
                vbox.getChildren().add(separator);
            }
        });

    }
}