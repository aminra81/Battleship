package ir.sharif.aminra.gameModels;

public class PlayerState {
    String username;
    int score;
    String state;

    public PlayerState(String username, int score, String state) {
        this.username = username;
        this.score = score;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public String getState() {
        return state;
    }

    public int getScore() {
        return score;
    }
}
