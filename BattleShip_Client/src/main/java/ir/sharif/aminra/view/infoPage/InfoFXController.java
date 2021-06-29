package ir.sharif.aminra.view.infoPage;

import ir.sharif.aminra.request.BackToMainRequest;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoFXController extends FXController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label losesLabel;

    @FXML
    private Label scoreLabel;

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public void setWinsLabel(int wins) {
        winsLabel.setText(String.valueOf(wins));
    }

    public void setLosesLabel(int loses) {
        losesLabel.setText(String.valueOf(loses));
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    @FXML
    public void back() {
        requestListener.listen(new BackToMainRequest());
    }
}
