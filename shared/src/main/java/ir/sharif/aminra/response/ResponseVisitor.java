package ir.sharif.aminra.response;

import ir.sharif.aminra.gameModels.GameState;
import ir.sharif.aminra.gameModels.LiveGameState;
import ir.sharif.aminra.gameModels.LiveState;
import ir.sharif.aminra.gameModels.PlayerState;

import java.util.List;

public interface ResponseVisitor {
    void enter(boolean success, String message);
    void goTo(String pageName, String message);
    void applyGameState(GameState gameState);
    void applyInfo(String username, int numberOfWins, int numberOfLoses);
    void logout();
    void applyScoreboard(List<PlayerState> playerStateList);
    void applyLiveGames(List<LiveGameState> liveGameStatesList);
    void applyLiveState(LiveState liveState);
}
