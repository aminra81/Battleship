package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.gameController.GameController;
import ir.sharif.aminra.controller.liveController.LiveController;
import ir.sharif.aminra.controller.network.RequestSender;
import ir.sharif.aminra.gameModels.GameState;
import ir.sharif.aminra.gameModels.LiveGameState;
import ir.sharif.aminra.gameModels.LiveState;
import ir.sharif.aminra.gameModels.PlayerState;
import ir.sharif.aminra.request.gameRequests.GetGameStateRequest;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.liveGamesRequests.GetLiveStateRequest;
import ir.sharif.aminra.request.liveGamesRequests.UpdateLiveGamesRequest;
import ir.sharif.aminra.request.scoreboardRequests.UpdateScoreboardRequest;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;
import ir.sharif.aminra.util.Loop;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.enterPage.EnterFXController;
import ir.sharif.aminra.view.gamePage.GameFXController;
import ir.sharif.aminra.view.gamePage.LiveFXController;
import ir.sharif.aminra.view.infoPage.InfoFXController;
import ir.sharif.aminra.view.liveGamesPage.GamePanelFXController;
import ir.sharif.aminra.view.liveGamesPage.LiveGamesFXController;
import ir.sharif.aminra.view.scoreboardPage.ScoreBoardFXController;
import ir.sharif.aminra.view.scoreboardPage.UserPanelFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class MainController implements ResponseVisitor {
    private final RequestSender requestSender;
    private final List<Request> requestsList;
    private final Loop loop, gameUpdater, livesUpdater;
    private final ViewManager viewManager;

    public MainController(RequestSender requestSender) {
        this.requestSender = requestSender;
        this.requestsList = new LinkedList<>();
        this.loop = new Loop(10, this::sendRequests);
        this.gameUpdater = new Loop(20, this::updatePage);
        this.livesUpdater = new Loop(1, this::updateLives);
        this.viewManager = new ViewManager(this::addRequest);
    }

    public void start(Stage stage) {
        loop.start();
        gameUpdater.start();
        livesUpdater.start();
        viewManager.start(stage);
    }


    private void addRequest(Request request) {
        synchronized (requestsList) {
            requestsList.add(request);
        }
    }

    private void sendRequests() {
        List<Request> tmpRequestsList;
        synchronized (requestsList) {
            tmpRequestsList = new LinkedList<>(requestsList);
            requestsList.clear();
        }
        for (Request request : tmpRequestsList) {
            Response response = requestSender.sendRequest(request);
            response.visit(this);
        }
    }

    private void updatePage() {
        if (viewManager.getCurPage() == null)
            return;
        if (viewManager.getCurPage().getFxController() instanceof GameFXController)
            addRequest(new GetGameStateRequest());
        else if(viewManager.getCurPage().getFxController() instanceof LiveFXController)
            addRequest(new GetLiveStateRequest());
        else if (viewManager.getCurPage().getFxController() instanceof ScoreBoardFXController)
            addRequest(new UpdateScoreboardRequest());
    }

    private void updateLives() {
        if(viewManager.getCurPage() == null)
            return;
        if (viewManager.getCurPage().getFxController() instanceof LiveGamesFXController)
            addRequest(new UpdateLiveGamesRequest());
    }

    @Override
    public void enter(boolean success, String message) {
        EnterFXController enterFXController = (EnterFXController) viewManager.getCurPage().getFxController();
        Platform.runLater(() -> {
            if (success)
                viewManager.setPage(new Page("mainPage"));
            else
                enterFXController.setErrorLabel(message);
        });
    }

    @Override
    public void goTo(String pageName, String message) {
        Platform.runLater(() -> {
            viewManager.setPage(new Page(pageName));
            if (message.length() > 0)
                viewManager.showAlert(message);
        });
    }

    @Override
    public void applyGameState(GameState gameState) {
        if (!(viewManager.getCurPage().getFxController() instanceof GameFXController))
            return;
        GameFXController gameFXController = (GameFXController) viewManager.getCurPage().getFxController();
        GameController gameController = new GameController(gameFXController, gameState);
        gameController.applyOnPanel();
    }

    @Override
    public void applyLiveState(LiveState liveState) {
        if(!(viewManager.getCurPage().getFxController() instanceof LiveFXController))
            return;
        LiveFXController liveFXController = (LiveFXController) viewManager.getCurPage().getFxController();
        LiveController liveController = new LiveController(liveFXController, liveState);
        liveController.applyOnPanel();
    }

    @Override
    public void applyInfo(String username, int numberOfWins, int numberOfLoses) {
        goTo("infoPage", "");
        Platform.runLater(() -> {
            InfoFXController infoFXController = (InfoFXController) viewManager.getCurPage().getFxController();
            infoFXController.setUsernameLabel(username);
            infoFXController.setWinsLabel(numberOfWins);
            infoFXController.setLosesLabel(numberOfLoses);
            infoFXController.setScoreLabel(numberOfWins - numberOfLoses);
        });
    }

    @Override
    public void logout() {
        goTo("enterPage", "");
    }

    @Override
    public void applyScoreboard(List<PlayerState> playerStateList) {
        Platform.runLater(() -> {
            if (!(viewManager.getCurPage().getFxController() instanceof ScoreBoardFXController))
                return;
            ScoreBoardFXController scoreBoardFXController = (ScoreBoardFXController) viewManager.getCurPage().getFxController();
            scoreBoardFXController.clear();

            for (int idx = 0; idx < playerStateList.size(); idx++) {
                PlayerState playerState = playerStateList.get(idx);
                Page userPage = new Page("userPanel");
                UserPanelFXController userPanelFXController = (UserPanelFXController) userPage.getFxController();
                userPanelFXController.setUsernameLabel(String.format("%s: %s", idx + 1, playerState.getUsername()));
                userPanelFXController.setScoreLabel(String.valueOf(playerState.getScore()));
                userPanelFXController.setStateLabel(playerState.getState());
                scoreBoardFXController.addPlayer(new AnchorPane(userPanelFXController.getPane()));
            }
        });
    }

    @Override
    public void applyLiveGames(List<LiveGameState> liveGameStateList) {
        Platform.runLater(() -> {
            if (!(viewManager.getCurPage().getFxController() instanceof LiveGamesFXController))
                return;
            LiveGamesFXController liveGamesFXController = (LiveGamesFXController) viewManager.getCurPage().getFxController();
            liveGamesFXController.clear();

            for (LiveGameState liveGameState : liveGameStateList) {
                Page liveGamePage = new Page("gamePanel");
                GamePanelFXController gamePanelFXController = (GamePanelFXController) liveGamePage.getFxController();
                gamePanelFXController.setGameNameLabel(liveGameState.getGameName());
                gamePanelFXController.setShootsLabel(liveGameState.getNumberOfShoots());
                gamePanelFXController.setDamagedShipsLabel(liveGameState.getNumberOfDamagesShips());
                gamePanelFXController.setDamagedCellsLabel(liveGameState.getNumberOfDamagesCells());
                gamePanelFXController.setRequestListener(this::addRequest);
                liveGamesFXController.addLiveGame(new AnchorPane(gamePanelFXController.getPane()));
            }
        });
    }
}
