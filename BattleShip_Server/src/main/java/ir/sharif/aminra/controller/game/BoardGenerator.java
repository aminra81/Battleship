package ir.sharif.aminra.controller.game;

import ir.sharif.aminra.gameModels.Board;
import ir.sharif.aminra.gameModels.Ship;

import java.util.Random;

public class BoardGenerator {
    Board[] boards = new Board[5];
    public BoardGenerator() {
        for (int idx = 0; idx < 5; idx++)
            boards[idx] = new Board();
        //Board 0
        boards[0].addShip(new Ship(0, 2, 3, false));
        boards[0].addShip(new Ship(1, 7, 1, false));
        boards[0].addShip(new Ship(1, 9, 2, true));
        boards[0].addShip(new Ship(2, 2, 2, false));
        boards[0].addShip(new Ship(4, 7, 3, true));
        boards[0].addShip(new Ship(5, 9, 1, false));
        boards[0].addShip(new Ship(6, 0, 2, false));
        boards[0].addShip(new Ship(7, 3, 1, false));
        boards[0].addShip(new Ship(9, 1, 4, false));
        boards[0].addShip(new Ship(9, 8, 1, false));
        //Board 1
        boards[1].addShip(new Ship(0, 2, 1, false));
        boards[1].addShip(new Ship(1, 5, 2, true));
        boards[1].addShip(new Ship(2, 0, 4, false));
        boards[1].addShip(new Ship(2, 9, 1, false));
        boards[1].addShip(new Ship(4, 0, 2, false));
        boards[1].addShip(new Ship(4, 7, 3, true));
        boards[1].addShip(new Ship(5, 3, 1, false));
        boards[1].addShip(new Ship(6, 1, 3, true));
        boards[1].addShip(new Ship(7, 3, 2, true));
        boards[1].addShip(new Ship(9, 8, 1, false));
        //Board 2
        boards[2].addShip(new Ship(0, 9, 1, false));
        boards[2].addShip(new Ship(1, 1, 2, true));
        boards[2].addShip(new Ship(3, 7, 3, false));
        boards[2].addShip(new Ship(4, 4, 2, true));
        boards[2].addShip(new Ship(5, 1, 2, false));
        boards[2].addShip(new Ship(5, 6, 4, true));
        boards[2].addShip(new Ship(7, 4, 1, false));
        boards[2].addShip(new Ship(8, 0, 3, false));
        boards[2].addShip(new Ship(8, 8, 1, false));
        boards[2].addShip(new Ship(9, 4, 1, false));
        //Board 3
        boards[3].addShip(new Ship(0, 0, 1, false));
        boards[3].addShip(new Ship(0, 3, 4, false));
        boards[3].addShip(new Ship(1, 9, 3, true));
        boards[3].addShip(new Ship(2, 1, 1, false));
        boards[3].addShip(new Ship(3, 7, 1, false));
        boards[3].addShip(new Ship(4, 5, 1, false));
        boards[3].addShip(new Ship(5, 1, 3, true));
        boards[3].addShip(new Ship(6, 3, 2, false));
        boards[3].addShip(new Ship(7, 6, 2 , false));
        boards[3].addShip(new Ship(9, 1, 2, false));
        //Board 4
        boards[4].addShip(new Ship(0, 3, 1, false));
        boards[4].addShip(new Ship(0, 8, 2, false));
        boards[4].addShip(new Ship(2, 2, 1, false));
        boards[4].addShip(new Ship(2, 5, 3, false));
        boards[4].addShip(new Ship(3, 0, 1, false));
        boards[4].addShip(new Ship(3, 9, 2, true));
        boards[4].addShip(new Ship(4, 4, 1, false));
        boards[4].addShip(new Ship(5, 0, 4, true));
        boards[4].addShip(new Ship(6, 2, 2, false));
        boards[4].addShip(new Ship(6, 7, 3, true));
    }

    public Board getBoard() {
        Random random = new Random();
        int boardNum = random.nextInt(5);
        return boards[boardNum];
    }
}
