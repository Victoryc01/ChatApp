package com.Victory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(1201);
            Socket s = ss.accept();
            System.out.println("Client connected!");

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // Thread for receiving messages from the client
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String msgin = din.readUTF();
                        if (msgin.equals("end")) {
                            System.out.println("Client disconnected.");
                            break;
                        }
                        System.out.println("Client: " + msgin);
                    }
                } catch (IOException e) {
                    System.out.println("Server error: " + e);
                }
            });

            // Thread for sending messages to the client
            Thread sendThread = new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String msgout;
                    while (true) {
                        msgout = br.readLine();
                        dout.writeUTF(msgout);
                        dout.flush();
                        if (msgout.equals("end")) {
                            System.out.println("Server disconnected.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server error: " + e);
                }
            });

            receiveThread.start();
            sendThread.start();

        } catch (IOException e) {
            System.out.println("Server error: " + e);
        }
    }
}