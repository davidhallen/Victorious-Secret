package bodega.timeline_alfa;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class ScoreBoard extends ActionBarActivity {

    Context context = this;
    Cursor cursor;
    private LinearLayout scoreLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        scoreLayout = (LinearLayout) findViewById(R.id.scoreLayout);
        getTopScores();

    }

    public void getTopScores(){

        TimelineDbHelper dbHelper;
        SQLiteDatabase db;
        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getReadableDatabase();
        cursor = dbHelper.getHighScore(db);

        if (cursor.moveToFirst()){
            do {
                Integer points; String playerName;

                points = cursor.getInt(0);
                playerName = cursor.getString(1);

                Button scoreButton = new Button(this);
                String buttonText = Integer.toString(points) + playerName;
                scoreButton.setText(buttonText);
                
            } while (cursor.moveToNext());
        }


    }




}


