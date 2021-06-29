package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.network.SocketResponseSender;
import ir.sharif.aminra.util.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager extends Thread{
    public void run() {
        try {
            Config config = Config.getConfig("server");
            int port = config.getProperty(Integer.class, "port") != null ?
                    config.getProperty(Integer.class, "port") : 8000;
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(new SocketResponseSender(socket));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
