package ir.sharif.aminra;

import ir.sharif.aminra.controller.SocketManager;

public class Main {
    public static void main(String[] args) {
        SocketManager socketManager = new SocketManager();
        socketManager.start();
    }
}
