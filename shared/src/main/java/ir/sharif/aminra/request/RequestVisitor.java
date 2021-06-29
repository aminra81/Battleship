package ir.sharif.aminra.request;

import ir.sharif.aminra.request.enterRequests.EnterRequestType;
import ir.sharif.aminra.response.Response;

public interface RequestVisitor {
    Response enter(String username, String password, EnterRequestType enterRequestType);
    Response startGame();
    Response getGameState();
    Response selectBoard(boolean isSelected);
    Response clickOnBoard(int x, int y);
    Response getInfo();
    Response backToMain();
    Response logout();
    Response getScoreboard();
    Response updateScoreboard();
    Response getLiveGames();
    Response updateLiveGames();
    Response startLive(String gameName);
    Response getLiveState();
}
