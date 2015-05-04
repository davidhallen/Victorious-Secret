package bodega.timeline_alfa;

/**
 * Created by Piotri on 5/1/2015.
 */

public class Player {

    private String name;
    private int score;

    Player () {
        score = 0;
    }

    public void setScore(int points) {
        score = score + 1;
    }

    public int getScore() {
        return score;
    }
}
