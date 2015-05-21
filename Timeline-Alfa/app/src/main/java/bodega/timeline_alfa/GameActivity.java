package bodega.timeline_alfa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class GameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        newGame();

    }

    public void newGame() {
        GameView gameView = new GameView(this, this);
        GameEngine gameEngine = new GameEngine(gameView, this);
        gameView.setEngine(gameEngine);
        gameEngine.startGame();
    }

    public void backToMenu() {
        startActivity(new Intent(GameActivity.this,MenuActivity.class));
    }

}


