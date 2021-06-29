package ir.sharif.aminra.view.liveGamesPage;

import ir.sharif.aminra.request.BackToMainRequest;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LiveGamesFXController extends FXController {
    @FXML
    private GridPane gamesBox;

    int idx = 0;

    public void clear() {
        gamesBox.getChildren().clear();
        idx = 0;
    }

    public void addLiveGame(AnchorPane pane) {
        gamesBox.add(pane, 0, idx++);
    }

    @FXML
    public void back() {
        requestListener.listen(new BackToMainRequest());
    }
}
