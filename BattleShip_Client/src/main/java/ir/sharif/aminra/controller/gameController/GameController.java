package ir.sharif.aminra.controller.gameController;

import ir.sharif.aminra.gameModels.GameState;
import ir.sharif.aminra.gameModels.GameStatus;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.view.gamePage.GameFXController;
import javafx.application.Platform;

public class GameController {
    private final GameFXController gameFXController;
    private final GameState gameState;
    private final Config gameConfig;

    public GameController(GameFXController gameFXController, GameState gameState) {
        this.gameFXController = gameFXController;
        this.gameState = gameState;
        this.gameConfig = Config.getConfig("gamePage");
    }

    public void applyOnPanel() {
        if(gameState == null)
            return;
        Platform.runLater(() -> {
            applyTimer();
            applyButtons();
            applyBoards();
            applyOpponentName();
        });
    }

    public void applyOpponentName() {
        gameFXController.setOpponentName(gameState.getOpponentName());
    }

    public void applyBoards() {
        gameFXController.applyMyBoard(gameState.getMyBoard());
        gameFXController.applyOpponentBoard(gameState.getOpponentBoard());
    }

    public void applyTimer() {
        Long remainingTime = gameState.getRemainingTime();
        if(remainingTime == null)
            gameFXController.setTime(gameConfig.getProperty("waitString"));
        else
            gameFXController.setTime(String.valueOf(remainingTime));
    }

    public void applyButtons() {
        gameFXController.setActivatedButtons(gameState.getGameStatus().equals(GameStatus.WAITING) &&
                gameState.getRemainingTime() != null);
    }
}
