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
import android.widget.TextView;
import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by ludvigsylven on 15-05-01.
 */
public class PlayersMenu extends ActionBarActivity {

    private static int nrOfPlayers = 1;
    private Button button6;
    //private ArrayList<Button> menuButtons;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button7;
    private Button button8;
    private Button lastClickedPlayerButton;

    private Resources res1;
    private Drawable s1;
    private Resources res2;
    private Drawable s2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_players);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        res1 = getResources();
        s1 = res1. getDrawable(R.drawable.playerbutton);
        res2 = getResources();
        s2 = res2. getDrawable(R.drawable.marked_playerbutton);
        button1 = (Button) findViewById(R.id.OnePlayer);
        button2 = (Button) findViewById(R.id.TwoPlayers);
        button3 = (Button) findViewById(R.id.ThreePlayers);
        button4 = (Button) findViewById(R.id.FourPlayers);
        button5 = (Button) findViewById(R.id.FivePlayers);
        button7 = (Button) findViewById(R.id.BackFromPlayer);
        button8 = (Button) findViewById(R.id.ChooseCategory);
        lastClickedPlayerButton = button1;
        button1.setBackground(s2);

        //menuButtons.add(button1);
        //menuButtons.add(button2);
        //menuButtons.add(button3);
        //menuButtons.add(button4);
        //menuButtons.add(button5);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*Intent intent = new Intent(GetExtras
                Intent.putextra*/
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
                startActivity(new Intent(PlayersMenu.this,Setup.class));
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PlayersMenu.this,Category.class));
            }
        });}

        /*
        public void reprintMenuButtons(View pressedButton) {
            for(Button b: menuButtons) {
                Resources res = getResources();
                Drawable shape = res. getDrawable(R.drawable.playerbutton);
                b.setBackground(shape);
            }
            Resources res = getResources();
            Drawable shape = res. getDrawable(R.drawable.marked_playerbutton);
            pressedButton.setBackground(shape);
        }*/



    public void setNrOfPlayers(int number) {
        nrOfPlayers = number;

    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

}
