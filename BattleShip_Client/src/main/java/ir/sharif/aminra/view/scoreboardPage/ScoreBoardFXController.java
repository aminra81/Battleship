package ir.sharif.aminra.view.scoreboardPage;

import ir.sharif.aminra.request.BackToMainRequest;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ScoreBoardFXController extends FXController {
    @FXML
    private GridPane usersBox;
    int idx = 0;

    @FXML
    public void back() {
        requestListener.listen(new BackToMainRequest());
    }

    public void clear() {
        usersBox.getChildren().clear();
        idx = 0;
    }

    public void addPlayer(AnchorPane pane) {
        usersBox.add(pane, 0, idx++);
    }
}
