package ir.sharif.aminra;

import ir.sharif.aminra.controller.MainController;
import ir.sharif.aminra.controller.network.SocketRequestSender;
import ir.sharif.aminra.util.Config;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Config config = Config.getConfig("client");
        int port = config.getProperty(Integer.class, "port") != null ?
                config.getProperty(Integer.class, "port") : 8000;
        String host = config.getProperty(String.class, "host") != null ?
                config.getProperty(String.class, "host") : "";
        Socket socket = new Socket(host, port);
        MainController mainController = new MainController(new SocketRequestSender(socket));
        mainController.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
