package com.example.calcclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private Text savedNumbers;
    @FXML
    private Text output;
    private Client client;
    private String operator = "";

    private String num1 = "";
    private String num2 = "";

    @FXML
    private void btnClicked(ActionEvent event) {
        String number = ((Button)event.getSource()).getText();
        output.setText(output.getText() + number);
        if (operator.equals("")) {
            num1 += number;
        } else {
            num2 += number;
        }
    }

    @FXML
    private void operatorClicked(ActionEvent event) {
        String operator = ((Button)event.getSource()).getText();
        if (operator.equals("=")) {
            if (!num1.isEmpty() && !num2.isEmpty() &&
                    !this.operator.isEmpty()) {
                client.sendRequestToMiddleware(num1, this.operator, num2);
                output.setText("");
                num1 = ""; num2 = ""; this.operator = "";
            }
        } else {
            if (this.operator.equals("")) {
                switch (operator) {
                    case "+":
                        this.operator = "1";
                        break;
                    case "-":
                        this.operator = "2";
                        break;
                    case "*":
                        this.operator = "3";
                        break;
                    default:
                        this.operator = "4";
                        break;
                }
                output.setText(output.getText() + operator);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("127.0.0.1", 1234));
        } catch(IOException e) {
            e.printStackTrace();
        }

        client.receiveResultFromMiddleware(savedNumbers);
    }

    public static void showMessage(RequestResult data, Text text) {
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
        String finalOP = operator;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                text.setText(finalOP);
            }
        });
    }
}