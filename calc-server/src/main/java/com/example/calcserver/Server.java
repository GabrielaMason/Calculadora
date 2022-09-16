package com.example.calcserver;

import java.io.*;
import java.net.Socket;

public class Server {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(Socket socket){
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter((socket.getOutputStream())));
        } catch (IOException e) {
            System.out.println("Error al iniciar servidor.");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveRequestFromMiddleware() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String data = bufferedReader.readLine();
                        String[] elements = data.split(",");
                        RequestResponse newResult = new RequestResponse(elements[0], elements[1], elements[2], elements[3], elements[4]);
                        if (newResult.type.equals("1")) {
                            try {
                                bufferedWriter.write("2" + "," +  newResult.operator + "," + newResult.num1 + "," + newResult.num2 + "," + calculateResult(newResult));
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                                System.out.println("Operation sent to server");
                            } catch (IOException e) {
                                System.out.println("Error sending operation to Middleware");
                                e.printStackTrace();
                                closeEverything(socket, bufferedReader, bufferedWriter);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error receiving message from the Middleware");
                        e.printStackTrace();
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public String calculateResult(RequestResponse data) {
        String result = "";
        double num1 = 0;
        double num2 = 0;
        try {
            num1 = Double.parseDouble(data.num1);
            num2 = Double.parseDouble(data.num2);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        switch (data.operator) {
            case "1":
                result = String.valueOf(num1 + num2);
                break;
            case "2":
                result = String.valueOf(num1 - num2);
                break;
            case "3":
                result = String.valueOf(num1 * num2);
                break;
            default:
                result = String.valueOf(num1 / num2);
                break;
        }
        return result;
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
