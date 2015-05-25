package bodega.timeline_alfa;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;

import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Piotri on 2015-05-16.
 */
public class GameView extends ActionBarActivity {
    public LinearLayout layout;
    public TextView question;
    public TextView messageBar;
    public Button answerButton;
    public Button backToMenu;

    public ArrayList<TextView> textViewArrayListScore = new ArrayList<TextView>();
    public TextView p1_score;
    private TextView p2_score;
    private TextView p3_score;
    private TextView p4_score;
    private TextView p5_score;
    public TextView lives_nr;

    public ArrayList<TextView> textViewArrayListPlayers = new ArrayList<TextView>();
    private TextView p1_text;
    private TextView p2_text;
    private TextView p3_text;
    private TextView p4_text;
    private TextView p5_text;
    public TextView lives_text;



    private GameEngine ge;
    Activity c;
    Context context;

    GameView(final GameActivity c) {
        this.c =  c;
        this.context = context;
        layout = (LinearLayout) c.findViewById(R.id.timelineLayout);
        question = (TextView) c.findViewById(R.id.question);
        messageBar = (TextView) c.findViewById(R.id.messageBar);
        answerButton = (Button) c.findViewById(R.id.answerButton);
        answerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ge.newCard();

            }
        });

        backToMenu = (Button) c.findViewById(R.id.backToMenuFromGame);
        backToMenu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                c.backToMenu();
            }
        });

        p1_text = (TextView) c.findViewById(R.id.player1_text);
        p2_text = (TextView) c.findViewById(R.id.player2_text);
        p3_text = (TextView) c.findViewById(R.id.player3_text);
        p4_text = (TextView) c.findViewById(R.id.player4_text);
        p5_text = (TextView) c.findViewById(R.id.player5_text);

        textViewArrayListPlayers.add(p1_text);
        textViewArrayListPlayers.add(p2_text);
        textViewArrayListPlayers.add(p3_text);
        textViewArrayListPlayers.add(p4_text);
        textViewArrayListPlayers.add(p5_text);

        p1_score = (TextView) c.findViewById(R.id.player1_score);
        p2_score = (TextView) c.findViewById(R.id.player2_score);
        p3_score = (TextView) c.findViewById(R.id.player3_score);
        p4_score = (TextView) c.findViewById(R.id.player4_score);
        p5_score = (TextView) c.findViewById(R.id.player5_score);

        textViewArrayListScore.add(p1_score);
        textViewArrayListScore.add(p2_score);
        textViewArrayListScore.add(p3_score);
        textViewArrayListScore.add(p4_score);
        textViewArrayListScore.add(p5_score);


    }

    public void setEngine(GameEngine gameEngine) {
        ge = gameEngine;
    }

    public void loadPlayerViews() {
        for (int i = 0; i < ge.getNrOfPlayers(); i++) {
            TextView tempPlayer = textViewArrayListPlayers.get(i);
            tempPlayer.setText("Player " + (i + 1) + ":");
        }

        if (ge.getNrOfPlayers() == 1) {
            lives_nr = p2_score;
            lives_text = p2_text;
            lives_nr.setText(String.valueOf(ge.getPlayer(0).getLives()));
            lives_text.setText("Lives");


            p3_score.setText("");
            p4_score.setText("");
            p5_score.setText("");
        }
        if (ge.getNrOfPlayers() == 2) {
            p3_score.setText("");
            p4_score.setText("");
            p5_score.setText("");
        }
        if (ge.getNrOfPlayers() == 3) {
            p4_score.setText("");
            p5_score.setText("");
        }
        if (ge.getNrOfPlayers() == 4) {
            p5_score.setText("");
        }

        textViewArrayListPlayers.get(ge.getActivePlayer()-1).setText("PLAYER " + ge.getActivePlayer() + ":");
        textViewArrayListPlayers.get(ge.getActivePlayer()-1).setTextColor(Color.parseColor("#699446"));
    }
}
