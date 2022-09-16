package com.example.calcclient;

import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter((socket.getOutputStream())));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al iniciar cliente.");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void sendRequestToMiddleware(String operator, String num1, String num2){
        try {
            bufferedWriter.write("1" + ',' + operator + ',' + num1 + ',' + num2 + ',' + " ");
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al mandar solicitud al middleware");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveResultFromMiddleware(Text text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()) {
                    try {
                        String resultFromMiddleware = bufferedReader.readLine();//Leer la solicitud del cliente
                        System.out.println(resultFromMiddleware);
                        String[] data = resultFromMiddleware.split(",");
                        RequestResult newResult = new RequestResult(data[0], data[1], data[2], data[3], data[4]);//Objeto con la respuesta
                        if (newResult.type.equals("2")) { //Mostrar la info en los controllers
                            ClientController.showMessage(newResult, text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error al recibir resultado del middleware.");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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
