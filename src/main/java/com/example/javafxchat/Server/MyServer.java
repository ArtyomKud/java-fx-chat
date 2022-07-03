package com.example.javafxchat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private final int PORT = 8189;

    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) { System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) { System.out.println("Ошибка в работе сервера");
        } finally { if (authService != null) {
            authService.stop();
        }
        }
    }
    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }
    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) { o.sendMsg(msg);
        }
    }
    public synchronized void personaltMsg(String name1,String name, String msg) {
        String strFromClient = msg;
        String[] parts = strFromClient.split("\\s");
        ArrayList<String> arr = new ArrayList();
        if (parts.length>2){
            for (int i =2;i<parts.length; i++){
                arr.add(parts[i]);
                //arr.add(" ");

            }
            msg=arr.toString().replace("[", "").replace("]", "");
            for (ClientHandler o : clients) {
                String nameClient = o.getName();
                if(nameClient.equals (name)) {
                 o.sendMsg(name1+": "+msg);
                 }

        }

        }


    }

    public synchronized void unsubscribe(ClientHandler o) { clients.remove(o);
    }
    public synchronized void subscribe(ClientHandler o) { clients.add(o);
    }
}
