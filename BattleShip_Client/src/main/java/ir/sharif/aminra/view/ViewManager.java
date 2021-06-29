package ir.sharif.aminra.view;

import ir.sharif.aminra.listeners.RequestListener;
import ir.sharif.aminra.util.Config;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Stack;

public class ViewManager {

    RequestListener requestListener;

    public ViewManager(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    Stage stage;
    Stack<Page> stack = new Stack<>();
    Page curPage;

    public void start(Stage stage) {
        this.stage = stage;

        curPage = new Page("enterPage");
        setPage(curPage);

        stage.setTitle(Config.getConfig("main").getProperty("projectName"));
        stage.setResizable(false);
        stage.show();
    }

    public void setPage(Page page) {
        page.setRequestListener(requestListener);
        curPage = page;
        stack.push(page);
        stage.setScene(page.getScene());
    }

    public Page getCurPage() { return curPage; }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }
}
