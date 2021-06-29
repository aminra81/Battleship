package ir.sharif.aminra.controller.gameController;

import ir.sharif.aminra.controller.game.Game;
import ir.sharif.aminra.gameModels.GameState;
import ir.sharif.aminra.gameModels.GameStatus;
import ir.sharif.aminra.gameModels.LiveState;
import ir.sharif.aminra.models.Player;
import ir.sharif.aminra.response.GoToResponse;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.gameResponses.GetGameStateResponse;
import ir.sharif.aminra.response.liveGamesResponses.GetLiveStateResponse;
import ir.sharif.aminra.util.Config;

public class GameController {
    Game runningGame;
    Player player;

    public GameController(Game runningGame, Player player) {
        this.runningGame = runningGame;
        this.player = player;
    }

    public int getSide() {
        int side = 0;
        if (!runningGame.getPlayers()[0].equals(player))
            side = 1;
        return side;
    }

    public Response getGameState() {
        if (runningGame == null)
            return new GetGameStateResponse(null);
        int side = getSide();
        if (runningGame.getGameStatus().equals(GameStatus.FIRST_PLAYER_WON)
                || runningGame.getGameStatus().equals(GameStatus.SECOND_PLAYER_WON)) {
            String opponentName = runningGame.getPlayers()[1 - side].getUsername();
            int winnerSide = 0;
            if (runningGame.getGameStatus().equals(GameStatus.SECOND_PLAYER_WON))
                winnerSide = 1;
            runningGame = null;
            if (side == winnerSide)
                return new GoToResponse("mainPage",
                        String.format("you won and %s lost the game!", opponentName));
            else
                return new GoToResponse("mainPage",
                        String.format("you lost and %s won the game!", opponentName));
        }

        Long remainingTime;
        if (runningGame.getRemainingTime()[side] == null)
            remainingTime = null;
        else
            remainingTime = runningGame.getRemainingTime()[side]
                    - ((System.currentTimeMillis() - runningGame.getStartedTimeCounter()[side]) / 1000);

        if (remainingTime != null && remainingTime <= 0) {
            runningGame.timeFinished(side);
            return getGameState();
        }

        return new GetGameStateResponse(new GameState(runningGame.getGameStatus(),
                runningGame.getPlayers()[1 - side].getUsername(), runningGame.getBoards()[side],
                runningGame.getBoards()[1 - side], remainingTime));
    }

    public Response getLiveState() {
        if (runningGame == null)
            return new GetLiveStateResponse(null);

        if (runningGame.getGameStatus().equals(GameStatus.FIRST_PLAYER_WON)
                || runningGame.getGameStatus().equals(GameStatus.SECOND_PLAYER_WON)) {
            String winnerName, loserName;
            if (runningGame.getGameStatus().equals(GameStatus.FIRST_PLAYER_WON)) {
                winnerName = runningGame.getPlayers()[0].getUsername();
                loserName = runningGame.getPlayers()[1].getUsername();
            } else {
                winnerName = runningGame.getPlayers()[1].getUsername();
                loserName = runningGame.getPlayers()[0].getUsername();
            }
            runningGame = null;
            return new GoToResponse("mainPage",
                    String.format("%s won and %s lost the game!", winnerName, loserName));
        }

        String turn;
        if (runningGame.getGameStatus().equals(GameStatus.WAITING))
            turn = Config.getConfig("livePage").getProperty("mapSelection");
        else if (runningGame.getRemainingTime()[0] == null)
            turn = String.format("%s's turn", runningGame.getPlayers()[1].getUsername());
        else
            turn = String.format("%s's turn", runningGame.getPlayers()[0].getUsername());

        String[] playerNames = {runningGame.getPlayers()[0].getUsername(), runningGame.getPlayers()[1].getUsername()};

        return new GetLiveStateResponse(new LiveState(runningGame.getGameStatus(),
                playerNames, runningGame.getBoards(), turn));
    }

    public Response selectBoard(boolean isSelected) {
        int side = getSide();
        if (isSelected)
            runningGame.setRemainingTime(side, null);
        else
            runningGame.changeBoard(side);
        return getGameState();
    }

    public Response clickOnBoard(int x, int y) {
        int side = getSide();
        runningGame.damageOnCell(x, y, side);
        return getGameState();
    }

    public Game getRunningGame() {
        return runningGame;
    }
}
