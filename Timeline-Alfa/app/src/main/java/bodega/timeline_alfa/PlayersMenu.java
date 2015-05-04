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

    private static int nrOfPlayers = 1;
    private Button button6;



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
        Button button7 = (Button) findViewById(R.id.BackFromPlayer);
        Button button8 = (Button) findViewById(R.id.ChooseCategory);

        button6 = (Button) findViewById(R.id.currentNumberPlayers);
        button6.setText(String.valueOf("Player(s):\n" + getNrOfPlayers()));

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(1);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(5);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PlayersMenu.this,Setup.class));
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PlayersMenu.this,Category.class));
            }
        });

    }

    public void setNrOfPlayers(int number) {
        nrOfPlayers = number;
        button6.setText(String.valueOf("Player(s):\n" + getNrOfPlayers()));
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

}