package bodega.timeline_alfa;

/**
 * Created by Piotri on 5/1/2015.
 */

public class Player {

    private String name;
    private int playerId;
    private int score;
    private int lives;

    public Player (int playerId) {
        this.playerId = playerId;
        name = "Player";
        score = 0;
        lives = 3;
    }

    public void setScore(int points) {
        score = score + points;
        if (score < 0)
            score = 0;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name + " " + playerId;
    }

    public void looseALife(){
        this.lives = lives - 1;

    }

    public int getLives(){
        return lives;

    }

    public void setLives(int lives){
        this.lives = lives;
    }
}


