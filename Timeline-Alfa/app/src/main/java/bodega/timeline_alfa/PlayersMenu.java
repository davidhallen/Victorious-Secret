package bodega.timeline_alfa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ludvigsylven on 15-05-01.
 */
public class PlayersMenu extends ActionBarActivity {

    private int nrOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_players);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button button1 = (Button) findViewById(R.id.OnePlayer);
        Button button2 = (Button) findViewById(R.id.TwoPlayers);
        Button button3 = (Button) findViewById(R.id.ThreePlayers);
        Button button4 = (Button) findViewById(R.id.FourPlayers);
        Button button5 = (Button) findViewById(R.id.FivePlayers);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(1);
                startActivity(new Intent(PlayersMenu.this, Setup.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(2);
                startActivity(new Intent(PlayersMenu.this,Setup.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(3);
                startActivity(new Intent(PlayersMenu.this,Setup.class));
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(4);
                startActivity(new Intent(PlayersMenu.this,Setup.class));

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(5);
                startActivity(new Intent(PlayersMenu.this,Setup.class));
            }
        });

    }

    public void setNrOfPlayers(int number) {
        nrOfPlayers = number;
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

}
