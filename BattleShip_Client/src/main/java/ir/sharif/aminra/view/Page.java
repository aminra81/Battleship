package ir.sharif.aminra.view;

import ir.sharif.aminra.Main;
import ir.sharif.aminra.listeners.RequestListener;
import ir.sharif.aminra.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class Page {
    private Scene scene;
    private FXController fxController;

    public Scene getScene() {
        return scene;
    }

    public FXController getFxController() { return fxController; }
    public Page(String fxmlName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Main.class.getResource(Config.getConfig("fxmls").getProperty(String.class, fxmlName)));
            this.scene = new Scene(fxmlLoader.load());
            this.fxController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRequestListener(RequestListener requestListener) {
        this.getFxController().setRequestListener(requestListener);
    }
}
