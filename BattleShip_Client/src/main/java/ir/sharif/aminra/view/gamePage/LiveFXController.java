package ir.sharif.aminra.view.gamePage;

import ir.sharif.aminra.gameModels.Board;
import ir.sharif.aminra.gameModels.LiveState;
import ir.sharif.aminra.gameModels.Ship;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class LiveFXController extends FXController implements Initializable {
    LiveState liveState;
    GraphicalCell[][][] playersBoard = new GraphicalCell[2][10][10];

    @FXML
    AnchorPane pane;

    @FXML
    private Label turnLabel;

    @FXML
    private Label firstPlayerNameLabel;

    @FXML
    private Label secondPlayerNameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Config gameConfig = Config.getConfig("gamePage");
        double myBoardX = gameConfig.getProperty(Double.class, "myBoardX");
        double opponentBoardX = gameConfig.getProperty(Double.class, "opponentBoardX");
        double boardsY = gameConfig.getProperty(Double.class, "boardsY");
        double sizeOfCell = gameConfig.getProperty(Double.class, "sizeOfCell");
        double damagedCircleRadius = gameConfig.getProperty(Double.class, "damagedCircleRadius");

        liveState = null;

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                playersBoard[0][x][y] = new GraphicalCell(x, y, true);
                Circle firstPlayerDamagedCircle = new Circle(myBoardX + y * sizeOfCell + sizeOfCell / 2,
                        boardsY + x * sizeOfCell + sizeOfCell / 2, damagedCircleRadius);
                firstPlayerDamagedCircle.setFill(Color.BLACK);
                firstPlayerDamagedCircle.setVisible(false);
                playersBoard[0][x][y].setDamagedCircle(firstPlayerDamagedCircle);

                playersBoard[1][x][y] = new GraphicalCell(x, y, false);
                Circle secondPlayerDamagedCircle = new Circle(opponentBoardX + y * sizeOfCell + sizeOfCell / 2,
                        boardsY + x * sizeOfCell + sizeOfCell / 2, damagedCircleRadius);
                secondPlayerDamagedCircle.setFill(Color.BLACK);
                secondPlayerDamagedCircle.setVisible(false);
                playersBoard[1][x][y].setDamagedCircle(secondPlayerDamagedCircle);

                pane.getChildren().add(playersBoard[0][x][y]);
                pane.getChildren().add(playersBoard[1][x][y]);
                pane.getChildren().add(firstPlayerDamagedCircle);
                pane.getChildren().add(secondPlayerDamagedCircle);
            }
        }
    }

    public void setFirstPlayerNameLabel(String firstPlayerName) {
        firstPlayerNameLabel.setText(firstPlayerName);
    }

    public void setSecondPlayerNameLabel(String secondPlayerName) {
        secondPlayerNameLabel.setText(secondPlayerName);
    }

    public void setTurnLabel(String turn) {
        turnLabel.setText(turn);
    }

    public String getShipColor(Ship ship) {
        Config gameConfig = Config.getConfig("gamePage");
        switch (ship.getSize()) {
            case 4:
                return gameConfig.getProperty("battleshipColor");
            case 3:
                return gameConfig.getProperty("cruiserColor");
            case 2:
                return gameConfig.getProperty("destroyerColor");
            case 1:
                return gameConfig.getProperty("frigateColor");
        }
        return null;
    }

    public void applyBoards(Board[] boards) {
        Config gameConfig = Config.getConfig("gamePage");
        for (int side = 0; side < 2; side++)
            for (int x = 0; x < 10; x++)
                for (int y = 0; y < 10; y++)
                    playersBoard[side][x][y].setFill(Color.web(gameConfig.getProperty("defaultColor")));

        for (int side = 0; side < 2; side++)
            for (Ship ship : boards[side].getShips()) {
                if (ship.getHealth() > 0)
                    continue;
                if (ship.isVertical())
                    for (int x = ship.getX(); x < ship.getX() + ship.getSize(); x++)
                        playersBoard[side][x][ship.getY()].setFill(Color.web(getShipColor(ship)));
                else
                    for (int y = ship.getY(); y < ship.getY() + ship.getSize(); y++)
                        playersBoard[side][ship.getX()][y].setFill(Color.web(getShipColor(ship)));
            }

        for (int side = 0; side < 2; side++)
            for (int x = 0; x < 10; x++)
                for (int y = 0; y < 10; y++)
                    if (boards[side].getCell(x, y).isDamaged())
                        playersBoard[side][x][y].getDamaged();
    }

}
