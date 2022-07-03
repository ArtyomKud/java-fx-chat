package com.example.javafxchat.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JavaChatClient {
    private final String serverAdres = "localhost";
    private final int serverPort = 8189;
    Socket socket=null;
    private DataInputStream in;
    private DataOutputStream out;
    String myNick="";

    private final JavaChatController javaChatController;

    public JavaChatClient(JavaChatController javaChatController) {
        this.javaChatController= javaChatController;


    }

    public  void openConnect() throws IOException {

            socket = new Socket(serverAdres, serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread (new Runnable() {

                @Override
                public void run() {

                    try {

                        while (true) {

                            String strFromServer = in.readUTF();
                            if(!javaChatController.authBoxStatus()){
                                javaChatController.setAuthorized(true);

                            }
                            if (strFromServer.startsWith("/authok")) {
                                javaChatController.setAuthorized(true);
                                break;
                            }


                        }

                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {

                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.equalsIgnoreCase("/end")) {
                                closedConnection();
                                break;
                            }
                                javaChatController.printText(strFromServer);


                        }


                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }

                }

            }).start();

    }



    public void sendMessage() throws IOException {
        out.writeUTF(javaChatController.outText());

    }
    public void closedConnection(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeUTF("/end");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void performAuth(){
        try {

            String login = String.valueOf(javaChatController.getLogin());
            String pass = String.valueOf(javaChatController.getPassword());
            out.writeUTF("/auth"+" "+login+" "+pass);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String readMsg (String str)  {


           if (str.equalsIgnoreCase("/end")) {
               closedConnection();
           }

       return str;
    }


}
