package com.Victory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1",1201);

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msgin="", msgout ="";

            while (!msgin.equals("end")){
                msgout = br.readLine();
                dout.writeUTF(msgout);
                dout.flush();
                msgin = din.readUTF();
                System.out.println("Server: "+msgin);
            }

        } catch (Exception e) {
           // throw new RuntimeException(e);
            System.out.println("check port"+ e);
        }

    }
}
