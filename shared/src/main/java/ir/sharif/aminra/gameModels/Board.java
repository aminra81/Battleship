package ir.sharif.aminra.gameModels;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Cell[][]cells;
    private final List<Ship> ships;

    public Board() {
        cells = new Cell[10][10];
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                cells[x][y] = new Cell();
        ships = new ArrayList<>();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void addShip(Ship ship) {
        ships.add(ship);
        if (ship.isVertical())
            for (int x = ship.getX(); x < ship.getX() + ship.getSize(); x++)
                cells[x][ship.getY()].setShip(ship);
        else
            for (int y = ship.getY(); y < ship.getY() + ship.getSize(); y++)
                cells[ship.getX()][y].setShip(ship);
    }

    public List<Ship> getShips() { return ships; }

    public void killShip(Ship ship) {
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                if(ship.isAdjacent(x, y))
                    getCell(x, y).getDamaged();
    }
}
