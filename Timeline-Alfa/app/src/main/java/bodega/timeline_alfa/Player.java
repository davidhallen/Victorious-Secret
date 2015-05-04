package bodega.timeline_alfa;

/**
 * Created by Piotri on 5/1/2015.
 */

public class Player {

    private String name;
    private int playerId;
    private int score;

    public Player (int playerId) {
        this.playerId = playerId;
        name = "Player";
        score = 0;
    }

    public void setScore(int points) {
        score = score + points;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name + " " + playerId;
    }
}
