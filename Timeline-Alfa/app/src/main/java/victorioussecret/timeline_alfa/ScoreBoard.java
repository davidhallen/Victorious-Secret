package victorioussecret.timeline_alfa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ScoreBoard extends ActionBarActivity {

    Context context = this;
    Cursor cursor;
    private LinearLayout scoreLayout;
    private Button buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        scoreLayout = (LinearLayout) findViewById(R.id.scoreLayout);
        scoreLayout.setOrientation(LinearLayout.VERTICAL);
        showHighScores();

        buttonBack = (Button) findViewById(R.id.BackFromHighScore);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ScoreBoard.this,ExtrasActivity.class));
            }
        });

    }

    private void showHighScores(){

        TimelineDbHelper dbHelper;
        SQLiteDatabase db;
        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getReadableDatabase();
        cursor = dbHelper.getHighScore(db);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER_HORIZONTAL;

        if (cursor.moveToFirst()){
            do {
                Integer points; String playerName;

                points = cursor.getInt(0);
                playerName = cursor.getString(1);
                Button scoreButton = new Button(this);
                String buttonText = Integer.toString(points) + "  points" + "\n" + "\n" + playerName;
                scoreButton.setTextSize(15);
                scoreButton.setBackgroundResource(R.drawable.scorefields);
                scoreButton.setText(buttonText);
                scoreButton.setLayoutParams(params);
                scoreButton.setWidth(1000);
                scoreLayout.addView(scoreButton);

            } while (cursor.moveToNext());
        }
        else{

            TextView title = (TextView) findViewById(R.id.title);

            title.setHint("No High Scores" + "\n" + "Play more!");
        }
    }




}


