package ir.sharif.aminra.gameModels;

public class Ship {
    private final int x, y;
    private final int size;
    private int health;
    private final boolean isVertical;

    public Ship(int x, int y, int size, boolean isVertical) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.health = size;
        this.isVertical = isVertical;
    }

    public void getDamaged() {
        health--;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    public boolean isVertical() { return isVertical; }
    public int getHealth() { return health; }
    public boolean isAdjacent(int cellX, int cellY) {
        if(isVertical) {
            for (int curX = x; curX < x + size; curX++)
                if (isAdjacent(curX, y, cellX, cellY))
                    return true;
        } else {
            for (int curY = y; curY < y + size; curY++)
                if (isAdjacent(x, curY, cellX, cellY))
                    return true;
        }
        return false;
    }

    private boolean isAdjacent(int curX, int curY, int cellX, int cellY) {
        return Math.abs(curX - cellX) <= 1 && Math.abs(curY - cellY) <= 1;
    }
}
