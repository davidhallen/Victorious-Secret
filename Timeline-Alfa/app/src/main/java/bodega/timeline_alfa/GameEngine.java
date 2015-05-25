package bodega.timeline_alfa;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Piotri on 2015-05-16.
 */
public class GameEngine extends ActionBarActivity {

    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private ArrayList <Question> yearlist = new ArrayList <Question> (); //for yearButtons not yet played
    private ArrayList <Question> playedYears = new ArrayList <Question> (); //for played yearButtons
    private Question currentQuestion;
    private Question firstSelectedYear;
    private Question secondSelectedYear;
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    private Player player3 = new Player(3);
    private Player player4 = new Player(4);
    private Player player5 = new Player(5);

    private int nrOfPlayers;
    private int activePlayer;

    private String selectedCategory;
    private int numberOfQuestions;
    private String m_Text="";

    private GameCard firstSelectedButton;
    private GameCard secondSelectedButton;

    private boolean gameOver;

    Context context;
    TimelineDbHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    GameView gv;
    GameActivity ga;

    public GameEngine(GameView gv, Context context) {
        this.gv = gv;
        this.context = context;
        ga = (GameActivity) context;
        gameOver = false;
        nrOfPlayers = PlayersMenu.getNrOfPlayers();

        activePlayer = 1;

        if(nrOfPlayers == 1){
            numberOfQuestions = 100;
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

        Question bigbang = new Question (-5000, "Biggie Bang Bong");
        Question ragnarok = new Question (2212, "Ragnarok!");

        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getReadableDatabase();

        cursor = dbHelper.getQuestion(db, selectedCategory,numberOfQuestions);

        if (cursor.moveToFirst()){
            do {
                String question; Integer year;
                question = cursor.getString(1);
                year = cursor.getInt(2);
                Question yearB = new Question(year, question);
                yearlist.add(yearB);

            } while (cursor.moveToNext());
        }

        playedYears.clear();
        playedYears.add(bigbang); playedYears.add(ragnarok);

        if(selectedCategory.equals("noSelectedCategory")){

        }
        else{

        }
    }

    public void startGame() {
        gv.loadPlayerViews();
        printButtons();
    }
  

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    public Player getPlayer(int nr) {
        return listOfPlayers.get(nr);
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void printButtons(){
        gv.answerButton.setEnabled(false);
        gv.layout.removeAllViews();
        firstSelectedYear = null;
        secondSelectedYear = null;

        Collections.sort(playedYears);

        if (!playedYears.isEmpty()) {
            for (int x = 0; x < playedYears.size(); x++) {
                GameCard gameCard = new GameCard(context, playedYears.get(x));

                gameCard.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        clickedYear(view);
                    }
                });
                gv.layout.addView(gameCard);
                gameCard.setId(playedYears.get(x).hashCode());
            }
        }
        newQuestion();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void clickedYear (View view){

        GameCard gc = (GameCard) view;

        Question tempYear = null;
        for (Question y : playedYears) {
            if (y.hashCode() == gc.getId()) {
                tempYear = y;
                break;
            }
        }

        if (firstSelectedYear == null && tempYear != secondSelectedYear){
            if (secondSelectedYear==null) {
                gc.setState("MARKED");
                firstSelectedYear = tempYear;
                firstSelectedButton = gc;
            }
            else if (isBeside(tempYear,secondSelectedYear)) {
                gc.setState("MARKED");
                firstSelectedYear = tempYear;
                firstSelectedButton = gc;
                gv.answerButton.setEnabled(true);
            }


        }

        else if(secondSelectedYear == null && tempYear!= firstSelectedYear) {
            if (firstSelectedYear==null) {
                gc.setState("MARKED");
                secondSelectedYear = tempYear;
                secondSelectedButton = gc;
            }
            else if (isBeside(tempYear, firstSelectedYear)) {
                gc.setState("MARKED");
                secondSelectedYear = tempYear;
                secondSelectedButton = gc;
                gv.answerButton.setEnabled(true);
            }


        }

        else if (tempYear == firstSelectedYear){
            gc.setState("NORMAL");
            firstSelectedYear = null;
            gv.answerButton.setEnabled(false);

        }

        else if (tempYear == secondSelectedYear){
            gc.setState("NORMAL");
            secondSelectedYear = null;
            gv.answerButton.setEnabled(false);

        }

        else {

            //do nothing
        }

    }

    public void newQuestion() {
        if (!yearlist.isEmpty()) {
            currentQuestion = yearlist.get(0);
            yearlist.remove(0);
            playedYears.add(currentQuestion);
            gv.question.setText(currentQuestion.getQuestion());
        } else {
            int highestScore = 0;
            Player winner = null;
            for (int i=0; i < nrOfPlayers; i++) {
                Player p = listOfPlayers.get(i);
                if (p.getScore() >= highestScore)
                    highestScore = p.getScore();
                    winner = p;

            }
            String gameWinners = "";
            int nrOfWinners = 0;
            for (int x=0; x < nrOfPlayers; x++) {
                if (winner.getScore() == listOfPlayers.get(x).getScore()) {
                    gameWinners = gameWinners + listOfPlayers.get(x).getName() + " ";
                    nrOfWinners++;
                }
            }
            if (nrOfWinners == 1)
                gv.question.setText("Congratulations! " + gameWinners + " is the winner!");
            else
                gv.question.setText("Congratulations! " + gameWinners + " are the winners!");
            gv.question.setTextColor(Color.parseColor("#699446"));
            gv.question.setText("No more questions, Game Over");

            gv.answerButton.setText("New Game");
            gameOver = true;
            gv.answerButton.setEnabled(true);




            //only add highScore if single-player game and all categories
            if(nrOfPlayers == 1 && selectedCategory == "ALL CATEGORIES"){
                //add highScore if it's a new highscore
                if (player1.getScore() > 0 && dbHelper.getNumberOfHighScores(db) < 4) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ga);
        builder.setTitle("YOU'RE THE BOSS.\nNEW HIGHSCORE!!!");
        // Set up the input
        final EditText input = new EditText(ga);
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

        // Go to ScoreBoard activity
        //startActivity(new Intent(this,ScoreBoard.class));

    }

    public boolean isBeside(Question year1, Question year2){

        int i = playedYears.indexOf(year1);
        int j = playedYears.indexOf(year2);

        if (Math.abs(i-j) > 1){
            return false;
        }

        else return true;

    }

    private void nextTurn() {

        gv.textViewArrayListPlayers.get(activePlayer-1).setText("Player " + (activePlayer) + ":");
        gv.textViewArrayListPlayers.get(activePlayer-1).setTextColor(-1);


        if (activePlayer < nrOfPlayers) {
            activePlayer++;
        } else {
            activePlayer = 1;
        }

        //aPtV.setText(String.valueOf(activePlayer));
        gv.textViewArrayListPlayers.get(activePlayer-1).setText("PLAYER " + (activePlayer) + ":");
        gv.textViewArrayListPlayers.get(activePlayer-1).setTextColor(Color.parseColor("#699446"));

    }

    public void newCard () {

        if (gameOver == false) {
            int i = Math.max(firstSelectedYear.getYear(), secondSelectedYear.getYear());
            int j = Math.min(firstSelectedYear.getYear(), secondSelectedYear.getYear());

            if (currentQuestion.getYear() <= i && currentQuestion.getYear() >= j) {


                //Set score
                listOfPlayers.get(activePlayer - 1).setScore(1);
                gv.textViewArrayListScore.get(activePlayer - 1).setTextColor(-1);
                gv.textViewArrayListScore.get(activePlayer - 1).setText(String.valueOf(listOfPlayers.get(activePlayer - 1).getScore()) + " p");
                gv.messageBar.setText("");

                printButtons();
                nextTurn();

            } else {
                if (nrOfPlayers == 1) {
                    listOfPlayers.get(activePlayer - 1).looseALife();
                    if ((listOfPlayers.get(activePlayer-1).getLives()) == 0) {

                        //messageBar.setText("You are here, good");
                        gv.question.setText("Game Over. You got "+listOfPlayers.get(activePlayer-1).getScore()+" points" );
                        gv.answerButton.setText("New Game");
                        gameOver = true;
                        gv.answerButton.setEnabled(true);
                        gv.layout.removeAllViews();

                        if (!playedYears.isEmpty()) {
                            for (int x = 0; x < playedYears.size(); x++) {
                                GameCard gameCard = new GameCard(context, playedYears.get(x));

                                gv.layout.addView(gameCard);
                                gameCard.setClickable(false);
                                gameCard.setId(playedYears.get(x).hashCode());

                            }
                        }


                        //only add highScore if single-player game and all categories
                        if (nrOfPlayers == 1 && selectedCategory == "ALL CATEGORIES") {
                            //add highScore if it's a new highscore
                            if (player1.getScore() > 0 && dbHelper.getNumberOfHighScores(db) < 5) {
                                addHighScore();
                            } else if (player1.getScore() > dbHelper.getLowestScore(db)) {
                                dbHelper.deleteHighScore(db);
                                addHighScore();
                            }

                        }
                        listOfPlayers.get(activePlayer-1).setScore(0);
                        listOfPlayers.get(activePlayer-1).setNewLives();
                        gv.lives_nr.setText("X_X");
                        //gv.p1_score.setText("0");

                    } else{
                        gv.messageBar.setText("Loose a life");
                        gv.lives_nr.setText(String.valueOf(listOfPlayers.get(activePlayer - 1).getLives()));
                        gv.textViewArrayListScore.get(activePlayer - 1).setText(String.valueOf(listOfPlayers.get(activePlayer - 1).getScore()) + " p");
                        //firstSelectedButton.setState("WRONG");
                        //secondSelectedButton.setState("WRONG");
                        firstSelectedYear = null;
                        secondSelectedYear = null;
                        gv.answerButton.setEnabled(false);
                        gv.messageBar.setText("Wrong, it occured in " + currentQuestion.getYear() + " " + currentQuestion.getYearLabel() + ".");
                        printButtons();
                    }

                }
                else {
                    gv.textViewArrayListScore.get(activePlayer - 1).setTextColor(-65536);
                    gv.messageBar.setText("Wrong, try again!");
                    listOfPlayers.get(activePlayer - 1).setScore(-1);
                    gv.textViewArrayListScore.get(activePlayer - 1).setText(String.valueOf(listOfPlayers.get(activePlayer - 1).getScore()) + " p");
                    firstSelectedButton.setState("WRONG");
                    secondSelectedButton.setState("WRONG");
                    firstSelectedYear = null;
                    secondSelectedYear = null;
                    gv.answerButton.setEnabled(false);

                }

            }

        }
        else {

            gv.answerButton.setText("Place Card");




            ga.newGame();

           // GameActivity a =(GameActivity) context;
           // a.newGame();

            //gv.init();


        }
    }
}
