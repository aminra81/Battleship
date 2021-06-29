package ir.sharif.aminra.view.gamePage;

import ir.sharif.aminra.gameModels.Board;
import ir.sharif.aminra.gameModels.GameState;
import ir.sharif.aminra.gameModels.Ship;
import ir.sharif.aminra.request.gameRequests.ClickOnBoardRequest;
import ir.sharif.aminra.request.gameRequests.SelectBoardRequest;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.view.FXController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFXController extends FXController implements Initializable {

    GameState gameState;
    GraphicalCell[][] myGraphicalBoard = new GraphicalCell[10][10];
    GraphicalCell[][] opponentGraphicalBoard = new GraphicalCell[10][10];

    EventHandler<MouseEvent> eventHandler = event -> {
        GraphicalCell graphicalCell = (GraphicalCell) event.getSource();
        if(graphicalCell.isMine())
            return;
        requestListener.listen(new ClickOnBoardRequest(graphicalCell.getCellX(), graphicalCell.getCellY()));
    };

    @FXML
    AnchorPane pane;

    @FXML
    private Button selectButton;

    @FXML
    private Button anotherBoardButton;

    @FXML
    private Label timeLabel;

    @FXML
    private Label opponentNameLabel;

    @FXML
    public void anotherBoard() {
        requestListener.listen(new SelectBoardRequest(false));
    }

    @FXML
    public void select() {
        requestListener.listen(new SelectBoardRequest(true));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Config gameConfig = Config.getConfig("gamePage");
        double myBoardX = gameConfig.getProperty(Double.class, "myBoardX");
        double opponentBoardX = gameConfig.getProperty(Double.class, "opponentBoardX");
        double boardsY = gameConfig.getProperty(Double.class, "boardsY");
        double sizeOfCell = gameConfig.getProperty(Double.class, "sizeOfCell");
        double damagedCircleRadius = gameConfig.getProperty(Double.class, "damagedCircleRadius");

        gameState = null;

        selectButton.setDisable(true);
        anotherBoardButton.setDisable(true);
        selectButton.setVisible(false);
        anotherBoardButton.setVisible(false);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                myGraphicalBoard[x][y] = new GraphicalCell(x, y, true);
                Circle myDamagedCircle = new Circle(myBoardX + y * sizeOfCell + sizeOfCell / 2,
                        boardsY + x * sizeOfCell + sizeOfCell / 2, damagedCircleRadius);
                myDamagedCircle.setFill(Color.BLACK);
                myDamagedCircle.setVisible(false);
                myGraphicalBoard[x][y].setDamagedCircle(myDamagedCircle);
                myGraphicalBoard[x][y].setOnMouseClicked(eventHandler);

                opponentGraphicalBoard[x][y] = new GraphicalCell(x, y, false);
                Circle opponentDamagedCircle = new Circle(opponentBoardX + y * sizeOfCell + sizeOfCell / 2,
                        boardsY + x * sizeOfCell + sizeOfCell / 2, damagedCircleRadius);
                opponentDamagedCircle.setFill(Color.BLACK);
                opponentDamagedCircle.setVisible(false);
                opponentGraphicalBoard[x][y].setDamagedCircle(opponentDamagedCircle);
                opponentGraphicalBoard[x][y].setOnMouseClicked(eventHandler);

                pane.getChildren().add(myGraphicalBoard[x][y]);
                pane.getChildren().add(opponentGraphicalBoard[x][y]);
                pane.getChildren().add(myDamagedCircle);
                pane.getChildren().add(opponentDamagedCircle);
            }
        }
    }

    public void setOpponentName(String opponentName) {
        opponentNameLabel.setText(opponentName);
    }

    public void setTime(String time) {
        timeLabel.setText(time);
    }

    public void setActivatedButtons(boolean activated) {
        if (activated) {
            selectButton.setDisable(false);
            anotherBoardButton.setDisable(false);
            selectButton.setVisible(true);
            anotherBoardButton.setVisible(true);
        } else {
            selectButton.setDisable(true);
            anotherBoardButton.setDisable(true);
            selectButton.setVisible(false);
            anotherBoardButton.setVisible(false);
        }
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

    public void applyMyBoard(Board myBoard) {
        Config gameConfig = Config.getConfig("gamePage");
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                myGraphicalBoard[x][y].setFill(Color.web(gameConfig.getProperty("defaultColor")));
        for (Ship ship : myBoard.getShips())
            if (ship.isVertical())
                for (int x = ship.getX(); x < ship.getX() + ship.getSize(); x++)
                    myGraphicalBoard[x][ship.getY()].setFill(Color.web(getShipColor(ship)));
            else
                for (int y = ship.getY(); y < ship.getY() + ship.getSize(); y++)
                    myGraphicalBoard[ship.getX()][y].setFill(Color.web(getShipColor(ship)));
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                if(myBoard.getCell(x, y).isDamaged())
                    myGraphicalBoard[x][y].getDamaged();
    }

    public void applyOpponentBoard(Board opponentBoard) {
        Config gameConfig = Config.getConfig("gamePage");
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                opponentGraphicalBoard[x][y].setFill(Color.web(gameConfig.getProperty("defaultColor")));
        for (Ship ship : opponentBoard.getShips()) {
            if(ship.getHealth() > 0)
                continue;
            if (ship.isVertical())
                for (int x = ship.getX(); x < ship.getX() + ship.getSize(); x++)
                    opponentGraphicalBoard[x][ship.getY()].setFill(Color.web(getShipColor(ship)));
            else
                for (int y = ship.getY(); y < ship.getY() + ship.getSize(); y++)
                    opponentGraphicalBoard[ship.getX()][y].setFill(Color.web(getShipColor(ship)));
        }
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                if(opponentBoard.getCell(x, y).isDamaged())
                    opponentGraphicalBoard[x][y].getDamaged();
    }
}
