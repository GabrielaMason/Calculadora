package com.example.calcmiddleware;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ElementHandler implements Runnable{
    public static ArrayList<ElementHandler> elementHandlers = new ArrayList<>();

    private Socket socket;

    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    String port;
    private VBox vbox;

    ElementHandler(Socket socket, VBox vbox){
        this.vbox = vbox;
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.port = String.valueOf(elementHandlers.size());
            elementHandlers.add(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public  void run(){
        String request;
        while (socket.isConnected()){
            try {
                request = bufferedReader.readLine();
                String[] data = request.split(",");
                DataComponent newData = new DataComponent(data[0], data[1], data[2], data[3], data[4]);
                broadcastData(request);
                MiddlewareController.addMessageLog(newData, vbox);//Controller
            } catch (IOException e){
                e.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastData(String request){
        for (ElementHandler elementHandler : elementHandlers) {
            try {
                if (!elementHandler.port.equals(port)) {
                    elementHandler.bufferedWriter.write(request);
                    elementHandler.bufferedWriter.newLine();
                    elementHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                System.out.println("Error broadcasting message");
                e.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeServerHandler(){
        elementHandlers.remove(this);
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeServerHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
