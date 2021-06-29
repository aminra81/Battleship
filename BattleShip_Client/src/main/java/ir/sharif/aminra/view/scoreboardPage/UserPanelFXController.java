package ir.sharif.aminra.view.scoreboardPage;

import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserPanelFXController extends FXController {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label stateLabel;

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public void setScoreLabel(String score) {
        scoreLabel.setText(score);
    }

    public void setStateLabel(String state) {
        stateLabel.setText(state);
    }

    public AnchorPane getPane() { return pane; }
}
