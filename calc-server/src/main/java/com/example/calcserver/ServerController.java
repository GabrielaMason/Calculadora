package com.example.calcserver;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    public Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new Server(new Socket("127.0.0.1", 1234));
            System.out.println("Conexión al middleware exitosa.");
        } catch(IOException e) {
            e.printStackTrace();
        }

        server.receiveRequestFromMiddleware();
    }
}

