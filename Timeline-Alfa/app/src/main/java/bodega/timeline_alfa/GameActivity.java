package bodega.timeline_alfa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
        if(savedInstanceState == null){
            newGame();
        } else{
            //Fix onPause() and onResume()
            //oldGame();
            newGame();
        }
    }

    public void newGame() {
        GameView gameView = new GameView(this);
        GameEngine gameEngine = new GameEngine(gameView, this);
        gameView.setEngine(gameEngine);
        gameEngine.startGame();
    }

    //public void oldGame() {
      //  GameView gameView = new GameView(this);
      //  GameEngine gameEngine = new GameEngine(gameView, this);
      //  gameView.setEngine(gameEngine);
      //  gameEngine.startGame();
    //}



    public void backToMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sure you want to exit game session?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(GameActivity.this, MenuActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}


