package ir.sharif.aminra.gameModels;

public class Cell {
    private boolean damaged;
    private Ship ship;

    public Cell() {
        this.damaged = false;
        this.ship = null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() { return ship; }

    public void getDamaged() {
        if(isDamaged())
            return;
        this.damaged = true;
        if(this.ship != null)
            this.ship.getDamaged();
    }

    public boolean isDamaged() { return damaged; }
}
