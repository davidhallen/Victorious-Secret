package bodega.timeline_alfa;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity {

    private LinearLayout layout;
    private TextView question;
    private Button answerButton;
    private ArrayList <yearButton> yearlist = new ArrayList <yearButton> (); //for yearButtons not yet played
    private ArrayList <yearButton> playedYears = new ArrayList <yearButton> (); //for played yearButtons
    private yearButton currentQuestion;
    private yearButton firstSelectedYear;
    private yearButton secondSelectedYear;
    private boolean gameOver;
    private int numOfPlayers;

    private Player player1 = new Player(1);
    private TextView p1_score;
    private Player player2 = new Player(2);
    private TextView p2_score;
    private Player player3 = new Player(3);
    private TextView p3_score;
    private Player player4 = new Player(4);
    private TextView p4_score;
    private Player player5 = new Player(5);
    private TextView p5_score;
    private PlayersMenu pm;
    private int nrOfPlayers;
    private int activePlayer;
    private int score;
    private TextView p1_text;
    private TextView p2_text;
    private TextView p3_text;
    private TextView p4_text;
    private TextView p5_text;


    private TextView aPtV;

    Context context = this;
    TimelineDbHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();
    }

    public void init(){
        gameOver = false;
        layout = (LinearLayout) findViewById(R.id.timelineLayout);
        question = (TextView) findViewById(R.id.question);
        answerButton = (Button) findViewById(R.id.answerButton);
        yearButton bigbang = new yearButton (-5000, "Biggie Bang Bong");
        yearButton ragnarok = new yearButton (2212, "Ragnarok!");
        yearButton y1 = new yearButton (1912, "OS i Stockholm");
        yearButton y2 = new yearButton (1986, "Palme går på bio");
        yearButton y3 = new yearButton (1492, "Columbus upptäcker Amerika?");
        yearButton y4 = new yearButton (0, "Jesus krist föds");
        yearButton y5 = new yearButton (1955, "Whisky börjar tillverkas i Sverige");
        yearButton y6 = new yearButton (1496, "Leonardo da Vinci misslyckas med ett flygmaskinstest");
        yearButton y7 = new yearButton (1959, "Varumärket Frisbee godkänns");
        yearlist.add(y1); yearlist.add(y2); yearlist.add(y3); yearlist.add(y4);yearlist.add(y5);yearlist.add(y6);
        yearlist.add(y7);
        p1_score = (TextView) findViewById(R.id.player1_score);
        p2_score = (TextView) findViewById(R.id.player2_score);
        p3_score = (TextView) findViewById(R.id.player3_score);
        p4_score = (TextView) findViewById(R.id.player4_score);
        p5_score = (TextView) findViewById(R.id.player5_score);
        pm = new PlayersMenu();
        nrOfPlayers = pm.getNrOfPlayers();
        activePlayer = 1;


        p1_text = (TextView) findViewById(R.id.player1_text);
        p2_text = (TextView) findViewById(R.id.player2_text);
        p3_text = (TextView) findViewById(R.id.player3_text);
        p4_text = (TextView) findViewById(R.id.player4_text);
        p5_text = (TextView) findViewById(R.id.player5_text);

        if(nrOfPlayers == 1){
            p1_text.setText("Player 1");
            p2_score.setText("");
            p3_score.setText("");
            p4_score.setText("");
            p5_score.setText("");

        }
        if(nrOfPlayers == 2){
            p1_text.setText("Player 1");
            p2_text.setText("Player 2");
            p3_score.setText("");
            p4_score.setText("");
            p5_score.setText("");
        }
        if(nrOfPlayers == 3){
            p1_text.setText("Player 1");
            p2_text.setText("Player 2");
            p3_text.setText("Player 3");
            p4_score.setText("");
            p5_score.setText("");
        }
        if(nrOfPlayers == 4){
            p1_text.setText("Player 1");
            p2_text.setText("Player 2");
            p3_text.setText("Player 3");
            p4_text.setText("Player 4");
            p5_score.setText("");
        }
        if(nrOfPlayers == 5){
            p1_text.setText("Player 1");
            p2_text.setText("Player 2");
            p3_text.setText("Player 3");
            p4_text.setText("Player 4");
            p5_text.setText("Player 5");
        }

        aPtV = (TextView) findViewById(R.id.ActivePlayer);
        aPtV.setText(String.valueOf(activePlayer));


        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getReadableDatabase();

        cursor = dbHelper.getQuestion(db);

        if (cursor.moveToFirst()){
            do {
                String question; Integer year;

                question = cursor.getString(1);
                year = cursor.getInt(2);
                yearButton yearB = new yearButton(year, question);
                yearlist.add(yearB);

            } while (cursor.moveToNext());
        }


       playedYears.clear();
       playedYears.add(bigbang); playedYears.add(ragnarok);
       Collections.shuffle(yearlist);
       printButtons();
        playedYears.clear();
        playedYears.add(bigbang); playedYears.add(ragnarok);
        Collections.shuffle(yearlist);
        printButtons();
    }


    public void newQuestion() {
        if (!yearlist.isEmpty()){
            currentQuestion = yearlist.get(0);
            yearlist.remove(0);
            playedYears.add(currentQuestion);
            question.setText(currentQuestion.getQuestion());
        }

        else {
                question.setText("Slut på frågor mannen, Game Over");
                answerButton.setText("Nytt spel");
                gameOver = true;
                answerButton.setEnabled(true);

            //add highScore if it's a new highscore
                if( dbHelper.getLowestScore(db)< player1.getScore()) {
                    dbHelper.deleteHighScore(db);
                    dbHelper.addHighScore(player1.getScore(), "AAA", db);

                }
                else{
                    //do nothing
                }


        }

    };


    public void newButton (View view) {

        if (gameOver == false) {
            int i = Math.max(firstSelectedYear.getYear(), secondSelectedYear.getYear());
            int j = Math.min(firstSelectedYear.getYear(), secondSelectedYear.getYear());

            if (currentQuestion.getYear() <= i && currentQuestion.getYear() >= j) {
                printButtons();

                if (activePlayer == 1) {
                    player1.setScore(1);
                    p1_score.setText(String.valueOf(player1.getScore()));
                }
                else if (activePlayer == 2) {
                    player2.setScore(1);
                    p2_score.setText(String.valueOf(player2.getScore()));
                }
                else if (activePlayer == 3) {
                    player3.setScore(1);
                    p3_score.setText(String.valueOf(player3.getScore()));
                }
                else if (activePlayer == 4) {
                    player4.setScore(1);
                    p4_score.setText(String.valueOf(player4.getScore()));
                }
                else if (activePlayer == 5) {
                    player5.setScore(1);
                    p5_score.setText(String.valueOf(player5.getScore()));
                }
                nextTurn();

            } else {
                question.setText("Fel, försök igen!  " + currentQuestion.getQuestion());
            }
        }

        else {
            answerButton.setText("Placera årtal");

            init();

        }
    }
    public void printButtons(){
        answerButton.setEnabled(false);
        layout.removeAllViews();
        firstSelectedYear = null;
        secondSelectedYear = null;

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(400,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(40,0,0,0);

        Collections.sort(playedYears);

        if (!playedYears.isEmpty()) {
            for (int x = 0; x < playedYears.size(); x++) {
                Button year = new Button(this);
                int s = playedYears.get(x).getYear();
                String q = playedYears.get(x).getQuestion();
                year.setText(""+s+ "             " + q);
                year.setBackgroundColor(Color.BLUE);
                year.setLayoutParams(layoutParams);
                if (x == 0){
                    year.setBackgroundResource(R.drawable.bigbangpicture);
                    year.setText("Big Bang");
                    year.setTextSize(20);
                }

                else if (x == playedYears.size()-1){
                    year.setBackgroundResource(R.drawable.ragnarok);
                    year.setText("Ragnarok!!!");
                }

                year.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        clickedYear(view);

                    }
                });
                layout.addView(year);
                year.setId(playedYears.get(x).hashCode());
            }
        }


        newQuestion();

    }



    public void clickedYear (View view){

        yearButton tempYear = null;
        for (yearButton y : playedYears) {
            if (y.hashCode() == view.getId()) {
                tempYear = y;
                break;
            }
        }

        if (firstSelectedYear == null && tempYear != secondSelectedYear){
            if (secondSelectedYear==null) {
                view.setBackgroundColor(Color.RED);
                firstSelectedYear = tempYear;
            }
            else if (isBeside(tempYear,secondSelectedYear)) {
                view.setBackgroundColor(Color.RED);
                firstSelectedYear = tempYear;
                answerButton.setEnabled(true);
            }


        }

        else if(secondSelectedYear == null && tempYear!= firstSelectedYear) {
            if (firstSelectedYear==null) {
                view.setBackgroundColor(Color.RED);
                secondSelectedYear = tempYear;
            }
            else if (isBeside(tempYear,firstSelectedYear)) {
                view.setBackgroundColor(Color.RED);
                secondSelectedYear = tempYear;
                answerButton.setEnabled(true);
            }


        }

        else if (tempYear == firstSelectedYear){
            view.setBackgroundColor(Color.BLUE);
            firstSelectedYear = null;
            answerButton.setEnabled(false);

        }

        else if (tempYear == secondSelectedYear){
            view.setBackgroundColor(Color.BLUE);
            secondSelectedYear = null;
            answerButton.setEnabled(false);

        }

        else {

            //do nothing
        }

    }




    public boolean isBeside(yearButton year1,yearButton year2){

        int i = playedYears.indexOf(year1);
        int j = playedYears.indexOf(year2);

        if (Math.abs(i-j) > 1){
            return false;
        }

        else return true;

    }
    /*public void addNrOfPlayers(int nrOfPlayers) {
        for (int i=1; i<=nrOfPlayers; i++) {
            playerList.add(new Player(i));
        }
    }*/

    private void nextTurn() {


        if (activePlayer < nrOfPlayers) {
            activePlayer++;
        }
        else {
            activePlayer = 1;
        }
        aPtV.setText(String.valueOf(activePlayer));

    }


}


