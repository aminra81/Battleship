package ir.sharif.aminra.controller.game;

import ir.sharif.aminra.controller.ClientHandler;

public class GameLobby {
    private ClientHandler waiting;
    private static GameLobby instance;

    public static GameLobby getInstance() {
        if(instance == null)
            instance = new GameLobby();
        return instance;
    }

    public synchronized void startGameRequest(ClientHandler clientHandler) {
        if (waiting == null) {
            waiting = clientHandler;
        } else {
            if (waiting != clientHandler) {
                Game game = new Game(waiting.getPlayer(), clientHandler.getPlayer());
                waiting.setRunningGame(game);
                clientHandler.setRunningGame(game);
                waiting = null;
            }
        }
    }
}
