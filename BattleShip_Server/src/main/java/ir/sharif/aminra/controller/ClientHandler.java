package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.enterController.EnterValidator;
import ir.sharif.aminra.controller.game.Game;
import ir.sharif.aminra.controller.game.GameLobby;
import ir.sharif.aminra.controller.gameController.GameController;
import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.db.Context;
import ir.sharif.aminra.gameModels.*;
import ir.sharif.aminra.models.Player;
import ir.sharif.aminra.models.PlayerStatus;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.request.enterRequests.EnterRequestType;
import ir.sharif.aminra.response.*;
import ir.sharif.aminra.response.enterResponses.EnterResponse;
import ir.sharif.aminra.response.infoResponses.GetInfoResponse;
import ir.sharif.aminra.response.liveGamesResponses.UpdateLiveGamesResponse;
import ir.sharif.aminra.response.scoreboardResponses.UpdateScoreboardResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientHandler extends Thread implements RequestVisitor {
    private final ResponseSender responseSender;
    private volatile boolean running;
    Player player;
    Game runningGame;

    public ClientHandler(ResponseSender responseSender) throws IOException {
        this.responseSender = responseSender;
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    public void run() {
        while (running) {
            responseSender.sendResponse(responseSender.getRequest().visit(this));
        }
        responseSender.close();
    }

    public void setRunningGame(Game game) {
        this.runningGame = game;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Response enter(String username, String password, EnterRequestType enterRequestType) {
        EnterValidator enterValidator = new EnterValidator();
        String error = enterValidator.getError(username, password, enterRequestType);
        if (error.equals("")) {
            if (enterRequestType.equals(EnterRequestType.SIGN_UP))
                player = new Player(username, password);
            else
                player = Context.getInstance().getPlayerDB().getPlayer(username);

            player.setPlayerStatus(PlayerStatus.ONLINE);
            Context.getInstance().getPlayerDB().saveIntoDB(player);

            return new EnterResponse(true, "");
        } else
            return new EnterResponse(false, error);
    }

    @Override
    public Response startGame() {
        GameLobby.getInstance().startGameRequest(this);
        return new GoToResponse("gamePage", "");
    }

    @Override
    public Response getGameState() {
        GameController gameController = new GameController(runningGame, player);
        Response response = gameController.getGameState();
        runningGame = gameController.getRunningGame();
        return response;
    }

    @Override
    public Response getLiveState() {
        GameController gameController = new GameController(runningGame, player);
        Response response = gameController.getLiveState();
        runningGame = gameController.getRunningGame();
        return response;
    }

    @Override
    public Response selectBoard(boolean isSelected) {
        GameController gameController = new GameController(runningGame, player);
        Response response = gameController.selectBoard(isSelected);
        runningGame = gameController.getRunningGame();
        return response;
    }

    @Override
    public Response clickOnBoard(int x, int y) {
        GameController gameController = new GameController(runningGame, player);
        Response response = gameController.clickOnBoard(x, y);
        runningGame = gameController.getRunningGame();
        return response;
    }

    @Override
    public Response getInfo() {
        return new GetInfoResponse(player.getUsername(), player.getNumberOfWins(), player.getNumberOfLoses());
    }

    @Override
    public Response backToMain() {
        return new GoToResponse("mainPage", "");
    }

    @Override
    public Response logout() {
        player.setPlayerStatus(PlayerStatus.OFFLINE);
        Context.getInstance().getPlayerDB().saveIntoDB(player);
        player = null;
        return new LogoutResponse();
    }

    @Override
    public Response getScoreboard() {
        return new GoToResponse("scoreboardPage", "");
    }

    @Override
    public Response updateScoreboard() {
        List<PlayerState> playerStateList = Context.getInstance().getPlayerDB().getPlayerStates();
        Comparator<PlayerState> byDateTime = Comparator.comparing(PlayerState::getScore).reversed();
        playerStateList.sort(byDateTime);
        return new UpdateScoreboardResponse(playerStateList);
    }

    @Override
    public Response getLiveGames() {
        return new GoToResponse("liveGamesPage", "");
    }

    @Override
    public Response updateLiveGames() {
        List<LiveGameState> liveGameStateList = new ArrayList<>();
        synchronized (Game.currentGames) {
            for (Game game : Game.currentGames) {
                String gameName = game.getPlayers()[0].getUsername() + " vs " + game.getPlayers()[1].getUsername();
                int[] numberOfShoots = new int[2];
                int[] numberOfDamagedShips = new int[2];
                int[] numberOfDamagedCells = new int[2];
                //update number of shoots and damaged cells.
                for (int x = 0; x < 10; x++)
                    for (int y = 0; y < 10; y++) {
                        if (game.getBoards()[1].getCell(x, y).isDamaged()) {
                            numberOfShoots[0]++;
                            if (game.getBoards()[1].getCell(x, y).getShip() != null)
                                numberOfDamagedCells[1]++;
                        }
                        if (game.getBoards()[0].getCell(x, y).isDamaged()) {
                            numberOfShoots[1]++;
                            if (game.getBoards()[0].getCell(x, y).getShip() != null)
                                numberOfDamagedCells[0]++;
                        }
                    }
                //update number of damaged ships.
                for (Ship ship : game.getBoards()[0].getShips())
                    if (ship.getHealth() == 0)
                        numberOfDamagedShips[0]++;
                for (Ship ship : game.getBoards()[1].getShips())
                    if (ship.getHealth() == 0)
                        numberOfDamagedShips[1]++;

                liveGameStateList.add(new LiveGameState(gameName, numberOfDamagedShips,
                        numberOfShoots, numberOfDamagedCells));
            }
        }
        return new UpdateLiveGamesResponse(liveGameStateList);
    }

    @Override
    public Response startLive(String gameName) {
        Game currentGame = null;
        for (Game game : Game.currentGames)
            if (gameName.equals(game.getPlayers()[0].getUsername() + " vs " + game.getPlayers()[1].getUsername()))
                currentGame = game;
        runningGame = currentGame;
        return new GoToResponse("livePage", "");
    }
}
