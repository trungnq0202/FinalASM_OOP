package models;

public class Player {
    private String playerName;
    private int score;

    Player(String playerName, int score){
        this.score = 0;

    }

    public int getScore() {
        return score;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }
}
