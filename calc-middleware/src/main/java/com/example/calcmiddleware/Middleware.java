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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!serverSocket.isClosed()) {
                        Socket socket = serverSocket.accept();
                        System.out.println("Element connected");
                        ElementHandler elementHandler = new ElementHandler(socket, vBox);
                        Thread thread = new Thread(elementHandler);
                        thread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    closeEverything();
                }
            }
        }).start();
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
