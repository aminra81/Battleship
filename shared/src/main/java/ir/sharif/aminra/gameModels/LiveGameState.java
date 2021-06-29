package ir.sharif.aminra.gameModels;

public class LiveGameState {
    String gameName;
    int[] numberOfDamagedShips;
    int[] numberOfShoots;
    int[] numberOfDamagedCells;

    public LiveGameState(String gameName, int[] numberOfDamagedShips, int[] numberOfShoots, int[] numberOfDamagedCells) {
        this.gameName = gameName;
        this.numberOfDamagedShips = numberOfDamagedShips;
        this.numberOfShoots = numberOfShoots;
        this.numberOfDamagedCells = numberOfDamagedCells;
    }

    public String getGameName() { return gameName; }
    public String getNumberOfDamagesShips() { return numberOfDamagedShips[0] + " - " + numberOfDamagedShips[1]; }
    public String getNumberOfDamagesCells() { return numberOfDamagedCells[0] + " - " + numberOfDamagedCells[1]; }
    public String getNumberOfShoots() { return numberOfShoots[0] + " - " + numberOfShoots[1]; }
}
