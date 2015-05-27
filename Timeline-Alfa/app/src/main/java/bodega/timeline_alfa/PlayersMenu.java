package bodega.timeline_alfa;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by ludvigsylven on 15-05-01.
 */
public class PlayersMenu extends ActionBarActivity {

    private static int nrOfPlayers = 1;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button7;
    private Button button8;
    private Button lastClickedPlayerButton;

    private Resources res;
    private Drawable s1;
    private Drawable s2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_players);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        res = getResources();
        s1 = res. getDrawable(R.drawable.playerbutton);
        s2 = res. getDrawable(R.drawable.marked_playerbutton);
        button1 = (Button) findViewById(R.id.OnePlayer);
        button2 = (Button) findViewById(R.id.TwoPlayers);
        button3 = (Button) findViewById(R.id.ThreePlayers);
        button4 = (Button) findViewById(R.id.FourPlayers);
        button5 = (Button) findViewById(R.id.FivePlayers);
        button7 = (Button) findViewById(R.id.PlayersBack);
        button8 = (Button) findViewById(R.id.PlayersNext);


        switch (nrOfPlayers) {
            case 1: lastClickedPlayerButton = button1;
                    break;
            case 2: lastClickedPlayerButton = button2;
                    break;
            case 3: lastClickedPlayerButton = button3;
                    break;
            case 4: lastClickedPlayerButton = button4;
                    break;
            case 5: lastClickedPlayerButton = button5;
                    break;
        }
        lastClickedPlayerButton.setBackground(s2);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setNrOfPlayers(1);
                lastClickedPlayerButton.setBackground(s1);
                v.setBackground(s2);
                lastClickedPlayerButton = (Button) v;
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(2);
                lastClickedPlayerButton.setBackground(s1);
                v.setBackground(s2);
                lastClickedPlayerButton = (Button) v;
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(3);
                lastClickedPlayerButton.setBackground(s1);
                v.setBackground(s2);
                lastClickedPlayerButton = (Button) v;
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(4);
                lastClickedPlayerButton.setBackground(s1);
                v.setBackground(s2);
                lastClickedPlayerButton = (Button) v;
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNrOfPlayers(5);
                lastClickedPlayerButton.setBackground(s1);
                v.setBackground(s2);
                lastClickedPlayerButton = (Button) v;
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PlayersMenu.this,MenuActivity.class));
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PlayersMenu.this,Category.class));
            }
        });}


    public static void setNrOfPlayers(int number) {
        nrOfPlayers = number;

    }

    public static int getNrOfPlayers() {
        return nrOfPlayers;
    }

}
