package bodega.timeline_alfa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.math.BigInteger;
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
    private String m_Text="";
    private Resources resCards;
    private Drawable d_card;
    private Drawable d_markedCard;
    private Drawable d_bigBang;
    private Drawable d_markedBigbang;
    private Drawable d_ragnarok;
    private Drawable d_markedRagnarok;
    private String selectedCategory;
    private int numberOfQuestions;


    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    private Player player3 = new Player(3);
    private Player player4 = new Player(4);
    private Player player5 = new Player(5);

    private ArrayList<TextView> textViewArrayListScore = new ArrayList<TextView>();
    private TextView p1_score;
    private TextView p2_score;
    private TextView p3_score;
    private TextView p4_score;
    private TextView p5_score;

    private ArrayList<TextView> textViewArrayListPlayers = new ArrayList<TextView>();
    private TextView p1_text;
    private TextView p2_text;
    private TextView p3_text;
    private TextView p4_text;
    private TextView p5_text;

    private PlayersMenu pm;
    private Category cat;
    private int nrOfPlayers;
    private int activePlayer;
    private int score;

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
        resCards = getResources();
        d_card = resCards.getDrawable(R.drawable.card);
        d_markedCard = resCards.getDrawable(R.drawable.marked_card);
        d_bigBang = resCards.getDrawable(R.drawable.bigbang);
        d_markedBigbang = resCards.getDrawable(R.drawable.marked_bigbang);
        d_ragnarok = resCards.getDrawable(R.drawable.ragnarrok);
        d_markedRagnarok = resCards.getDrawable(R.drawable.marked_ragnarok);

        //pm = new PlayersMenu();
        nrOfPlayers = PlayersMenu.getNrOfPlayers();
        activePlayer = 1;

        if(nrOfPlayers == 1){
            numberOfQuestions = 5;
        }
        else {
            numberOfQuestions = 3*nrOfPlayers;
        }

        selectedCategory = Category.getSelectedCategory().toUpperCase();

        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        listOfPlayers.add(player4);
        listOfPlayers.add(player5);

        p1_text = (TextView) findViewById(R.id.player1_text);
        p2_text = (TextView) findViewById(R.id.player2_text);
        p3_text = (TextView) findViewById(R.id.player3_text);
        p4_text = (TextView) findViewById(R.id.player4_text);
        p5_text = (TextView) findViewById(R.id.player5_text);

        textViewArrayListPlayers.add(p1_text);
        textViewArrayListPlayers.add(p2_text);
        textViewArrayListPlayers.add(p3_text);
        textViewArrayListPlayers.add(p4_text);
        textViewArrayListPlayers.add(p5_text);

        p1_score = (TextView) findViewById(R.id.player1_score);
        p2_score = (TextView) findViewById(R.id.player2_score);
        p3_score = (TextView) findViewById(R.id.player3_score);
        p4_score = (TextView) findViewById(R.id.player4_score);
        p5_score = (TextView) findViewById(R.id.player5_score);

        textViewArrayListScore.add(p1_score);
        textViewArrayListScore.add(p2_score);
        textViewArrayListScore.add(p3_score);
        textViewArrayListScore.add(p4_score);
        textViewArrayListScore.add(p5_score);


        for(int i = 0; i < nrOfPlayers; i++){
            TextView tempPlayer = textViewArrayListPlayers.get(i);
            tempPlayer.setText("Player " + (i+1) + ":");
        }

        if(nrOfPlayers == 1){
            p2_score.setText("");
            p3_score.setText("");
            p4_score.setText("");
            p5_score.setText("");
        }
        if(nrOfPlayers == 2){
            p3_score.setText("");
            p4_score.setText("");
            p5_score.setText("");
        }
        if(nrOfPlayers == 3){
            p4_score.setText("");
            p5_score.setText("");
        }
        if(nrOfPlayers == 4){
            p5_score.setText("");
        }


        textViewArrayListPlayers.get(activePlayer-1).setText("PLAYER " + activePlayer + ":");
        textViewArrayListPlayers.get(activePlayer-1).setTextColor(-16711936);


        //aPtV = (TextView) findViewById(R.id.ActivePlayer);
        //aPtV.setText(String.valueOf(activePlayer));

        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getReadableDatabase();

        cursor = dbHelper.getQuestion(db, selectedCategory,numberOfQuestions);

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
       //Collections.shuffle(yearlist);
       printButtons();
    }


    public void newQuestion() {
        if (!yearlist.isEmpty()) {
            currentQuestion = yearlist.get(0);
            yearlist.remove(0);
            playedYears.add(currentQuestion);
            question.setText(currentQuestion.getQuestion());
        } else {
            question.setText("Slut på frågor mannen, Game Over");
            answerButton.setText("Nytt spel");
            gameOver = true;
            answerButton.setEnabled(true);

            //only add highScore if single-player game and all categories
            if(nrOfPlayers == 1 && selectedCategory == "NOSELECTEDCATEGORY"){
                //add highScore if it's a new highscore
                if (player1.getScore() > 0 && dbHelper.getNumberOfHighScores(db) < 5) {
                    addHighScore();
                }
                else if (player1.getScore() > dbHelper.getLowestScore(db)) {
                    dbHelper.deleteHighScore(db);
                    addHighScore();
                }
            }
        }
    }

    public void addHighScore() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("YOU'RE THE BOSS.\nNEW HIGHSCORE!!!");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                dbHelper.addHighScore(player1.getScore(), m_Text, db);
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


    public void newButton (View view) {

        if (gameOver == false) {
            int i = Math.max(firstSelectedYear.getYear(), secondSelectedYear.getYear());
            int j = Math.min(firstSelectedYear.getYear(), secondSelectedYear.getYear());

            if (currentQuestion.getYear() <= i && currentQuestion.getYear() >= j) {


                //First way of setting score

                listOfPlayers.get(activePlayer-1).setScore(1);
                textViewArrayListScore.get(activePlayer-1).setTextColor(-1);
                textViewArrayListScore.get(activePlayer-1).setText(String.valueOf(listOfPlayers.get(activePlayer-1).getScore())+ " p");
                printButtons();

                //Second way of setting score


                /*if (activePlayer == 1) {
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
                }*/
                nextTurn();

            } else {

                textViewArrayListScore.get(activePlayer-1).setTextColor(-65536);
                question.setText("Fel, försök igen!  " + currentQuestion.getQuestion());
                listOfPlayers.get(activePlayer-1).setScore(-1);
                textViewArrayListScore.get(activePlayer-1).setText(String.valueOf(listOfPlayers.get(activePlayer-1).getScore()) + " p");

                //nextTurn();
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
                year.setText(s + "\n" + q);
                year.setBackground(d_card);
                year.setLayoutParams(layoutParams);
                if (x == 0){
                    year.setBackground(d_bigBang);
                    year.setText("Big Bang!");
                    year.setTextSize(17);
                }

                else if (x == playedYears.size()-1){

                    year.setBackgroundResource(R.drawable.ragnarokpicture);

                    year.setBackground(d_ragnarok);
                    year.setText("Domedagen!");
                    year.setTextSize(17);
                    year.setBackgroundResource(R.drawable.ragnarrok);

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
                view.setBackground(d_markedCard);
                firstSelectedYear = tempYear;
            }
            else if (isBeside(tempYear,secondSelectedYear)) {
                view.setBackground(d_markedCard);
                firstSelectedYear = tempYear;
                answerButton.setEnabled(true);
            }


        }

        else if(secondSelectedYear == null && tempYear!= firstSelectedYear) {
            if (firstSelectedYear==null) {
                view.setBackground(d_markedCard);
                secondSelectedYear = tempYear;
            }
            else if (isBeside(tempYear,firstSelectedYear)) {
                view.setBackground(d_markedCard);
                secondSelectedYear = tempYear;
                answerButton.setEnabled(true);
            }


        }

        else if (tempYear == firstSelectedYear){
            view.setBackground(d_card);
            firstSelectedYear = null;
            answerButton.setEnabled(false);

        }

        else if (tempYear == secondSelectedYear){
            view.setBackground(d_card);
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

        textViewArrayListPlayers.get(activePlayer-1).setText("Player " + (activePlayer) + ":");
        textViewArrayListPlayers.get(activePlayer-1).setTextColor(-1);


        if (activePlayer < nrOfPlayers) {
            activePlayer++;
        }
        else {
            activePlayer = 1;
        }
        //aPtV.setText(String.valueOf(activePlayer));
        textViewArrayListPlayers.get(activePlayer-1).setText("PLAYER " + (activePlayer) + ":");
        textViewArrayListPlayers.get(activePlayer-1).setTextColor(-16711936);

    }


}


