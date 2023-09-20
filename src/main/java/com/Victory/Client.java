package com.Victory;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {

        try {
            Socket s = new Socket("127.0.0.1", 1201);

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // Thread for receiving messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String msgin = din.readUTF();
                        if (msgin.equals("end")) {
                            System.out.println("Server disconnected.");
                            break;
                        }
                        System.out.println("Server: " + msgin);
                    }
                } catch (IOException e) {
                    System.out.println("Client error: " + e);
                }
            });

            // Thread for sending messages to the server
            Thread sendThread = new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String msgout;
                    while (true) {
                        msgout = br.readLine();
                        dout.writeUTF(msgout);
                        dout.flush();
                        if (msgout.equals("end")) {
                            System.out.println("Client disconnected.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Client error: " + e);
                }
            });

            receiveThread.start();
            sendThread.start();

        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}
