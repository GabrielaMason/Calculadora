package com.example.calcmiddleware;

import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Middleware {
    private ServerSocket serverSocket;

    public Middleware(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startMiddleware(VBox vBox) {
        int idx = 0;
        try {
            while (!serverSocket.isClosed()){
                if (idx < 4) {
                    Socket socket = serverSocket.accept();
                    ElementHandler elementHandler = new ElementHandler(socket, vBox);
                    Thread thread = new Thread(elementHandler);
                    thread.start();
                    idx++;
                } else {
                    closeEverything();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeEverything(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
