package ir.sharif.aminra.view.liveGamesPage;

import ir.sharif.aminra.request.liveGamesRequests.StartLiveRequest;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GamePanelFXController extends FXController {

    String gameName;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label gameNameLabel;

    @FXML
    private Label shootsLabel;

    @FXML
    private Label damagedCellsLabel;

    @FXML
    private Label damagedShipsLabel;

    public void setGameNameLabel(String gameName) {
        this.gameName = gameName;
        gameNameLabel.setText(gameName);
    }

    public void setShootsLabel(String shoots) {
        shootsLabel.setText(shoots);
    }

    public void setDamagedCellsLabel(String damagedCells) {
        damagedCellsLabel.setText(damagedCells);
    }

    public void setDamagedShipsLabel(String damagedShips) {
        damagedShipsLabel.setText(damagedShips);
    }

    @FXML
    public void show() {
        requestListener.listen(new StartLiveRequest(gameName));
    }

    public AnchorPane getPane() { return pane; }
}
