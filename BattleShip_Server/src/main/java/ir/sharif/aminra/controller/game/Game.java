package ir.sharif.aminra.controller.game;

import ir.sharif.aminra.db.Context;
import ir.sharif.aminra.gameModels.Board;
import ir.sharif.aminra.gameModels.GameStatus;
import ir.sharif.aminra.gameModels.Ship;
import ir.sharif.aminra.models.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final List<Game> currentGames = new ArrayList<>();

    GameStatus gameStatus;
    Player[] players = new Player[2];
    Board[] boards = new Board[2];
    Integer[] remainingTime = new Integer[2];
    Long[] startedTimeCounter = new Long[2];
    Integer[] changedTimes = new Integer[2];
    BoardGenerator[] boardGenerators = new BoardGenerator[2];

    public Game(Player firstPlayer, Player secondPlayer) {
        gameStatus = GameStatus.WAITING;
        players[0] = firstPlayer;
        players[1] = secondPlayer;

        boardGenerators[0] = new BoardGenerator();
        boardGenerators[1] = new BoardGenerator();

        boards[0] = boardGenerators[0].getBoard();
        boards[1] = boardGenerators[1].getBoard();

        remainingTime[0] = 30;
        remainingTime[1] = 30;
        startedTimeCounter[0] = System.currentTimeMillis();
        startedTimeCounter[1] = System.currentTimeMillis();
        changedTimes[0] = 0;
        changedTimes[1] = 0;
        synchronized (currentGames) {
            currentGames.add(this);
        }
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Board[] getBoards() {
        return boards;
    }

    public Integer[] getRemainingTime() {
        return remainingTime;
    }

    public Long[] getStartedTimeCounter() {
        return startedTimeCounter;
    }

    public synchronized void setRemainingTime(int idx, Integer time) {
        remainingTime[idx] = time;
        if (time == null)
            checkForFinishWaiting();
    }

    public synchronized void checkForFinishWaiting() {
        if (!gameStatus.equals(GameStatus.WAITING))
            return;
        if (remainingTime[0] != null || remainingTime[1] != null)
            return;
        gameStatus = GameStatus.RUNNING;
        startedTimeCounter[0] = System.currentTimeMillis();
        remainingTime[0] = 25;
    }

    public synchronized void changeBoard(int idx) {
        boards[idx] = boardGenerators[idx].getBoard();
        changedTimes[idx]++;
        remainingTime[idx] += 10;
        if (changedTimes[idx] == 3)
            setRemainingTime(idx, null);
    }

    public synchronized void timeFinished(int idx) {
        if (gameStatus.equals(GameStatus.WAITING))
            setRemainingTime(idx, null);
        else if (gameStatus.equals(GameStatus.RUNNING)) {
            setRemainingTime(idx, null);
            setRemainingTime(1 - idx, 25);
            startedTimeCounter[1 - idx] = System.currentTimeMillis();
        }
    }

    public synchronized void checkForEnd() {
        Integer winnerSide = null;
        for (int side = 0; side < 2; side++) {
            boolean isLost = true;
            for (Ship ship : boards[side].getShips())
                if(ship.getHealth() > 0)
                    isLost = false;
            if(isLost)
                winnerSide = 1 - side;
        }
        if(winnerSide == null)
            return;
        synchronized (currentGames) {
            currentGames.remove(this);
        }
        if(winnerSide == 0)
            gameStatus = GameStatus.FIRST_PLAYER_WON;
        else
            gameStatus = GameStatus.SECOND_PLAYER_WON;

        players[winnerSide].addWin();
        players[1 - winnerSide].addLose();
        Context.getInstance().getPlayerDB().saveIntoDB(players[winnerSide]);
        Context.getInstance().getPlayerDB().saveIntoDB(players[1 - winnerSide]);
    }

    public synchronized void damageOnCell(int x, int y, int idx) {
        if(remainingTime[idx] == null)
            return;
        if(boards[1 - idx].getCell(x, y).isDamaged())
            return;
        boards[1 - idx].getCell(x, y).getDamaged();
        if(boards[1 - idx].getCell(x, y).getShip() == null)
            timeFinished(idx);
        else {
            //check if ship is completely damaged.
            Ship ship = boards[1 - idx].getCell(x, y).getShip();
            if(ship.getHealth() == 0)
                boards[1 - idx].killShip(ship);
            startedTimeCounter[idx] = System.currentTimeMillis();
            remainingTime[idx] = 25;
            checkForEnd();
        }
    }
}
