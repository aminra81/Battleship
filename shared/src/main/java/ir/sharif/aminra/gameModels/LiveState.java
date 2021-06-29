package ir.sharif.aminra.gameModels;

public class LiveState {
    private final GameStatus gameStatus;
    private final String players[];
    private final Board boards[];
    private final String turn;

    public LiveState(GameStatus gameStatus, String[] players, Board[] boards, String turn) {
        this.gameStatus = gameStatus;
        this.players = players;
        this.boards = boards;
        this.turn = turn;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String[] getPlayers() {
        return players;
    }

    public Board[] getBoards() {
        return boards;
    }

    public String getTurn() {
        return turn;
    }
}
