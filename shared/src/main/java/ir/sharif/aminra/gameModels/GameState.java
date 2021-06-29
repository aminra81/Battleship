package ir.sharif.aminra.gameModels;

public class GameState {
    private final GameStatus gameStatus;
    private final String opponentName;
    private final Board myBoard;
    private final Board opponentBoard;
    private final Long remainingTime;

    public GameState(GameStatus gameStatus, String opponentName, Board myBoard, Board opponentBoard, Long remainingTime) {
        this.gameStatus = gameStatus;
        this.opponentName = opponentName;
        this.myBoard = myBoard;
        this.opponentBoard = opponentBoard;
        this.remainingTime = remainingTime;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public Long getRemainingTime() {
        return remainingTime;
    }
}
