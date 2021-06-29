package ir.sharif.aminra.models;

import ir.sharif.aminra.db.Context;

import java.util.Objects;

public class Player {
    ID ID;
    String username;
    String password;
    int numberOfWins, numberOfLoses;
    PlayerStatus playerStatus;

    public Player(String username, String password) {
        this.ID = new ID(true);
        this.username = username;
        this.password = password;
        numberOfWins = 0;
        numberOfLoses = 0;
        playerStatus = PlayerStatus.ONLINE;

        Context.getInstance().getPlayerDB().saveIntoDB(this);

    }

    public int getScore() { return numberOfWins - numberOfLoses; }
    public int getNumberOfWins() { return numberOfWins; }
    public int getNumberOfLoses() { return numberOfLoses; }
    public void addWin() { numberOfWins++; }
    public void addLose() { numberOfLoses++; }
    public ID getID() { return this.ID; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public PlayerStatus getPlayerStatus() { return playerStatus; }
    public void setPlayerStatus(PlayerStatus playerStatus) { this.playerStatus = playerStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return player.getID().equals(getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
