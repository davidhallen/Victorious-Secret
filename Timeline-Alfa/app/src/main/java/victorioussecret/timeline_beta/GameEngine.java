package victorioussecret.timeline_beta;

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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Piotri on 2015-05-16.
 * This class handles in-game logic and events
 */


public class GameEngine extends ActionBarActivity {

    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private ArrayList<Question> yearlist = new ArrayList<Question>(); //for questions not yet played
    private ArrayList<Question> playedYears = new ArrayList<Question>(); //for played questions
    private Question currentQuestion;
    private Question firstSelectedYear;
    private Question secondSelectedYear;
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    private Player player3 = new Player(3);
    private Player player4 = new Player(4);
    private Player player5 = new Player(5);

    private int nrOfPlayers;
    private int activePlayerNr;

    private String selectedCategory;
    private int numberOfQuestions;
    private int nrOfQuestionsPerPlayer = 2;
    private String m_Text = "";

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
        nrOfPlayers = PlayersMenuActivity.getNrOfPlayers();

        activePlayerNr = 1;

        if (nrOfPlayers == 1) {
            numberOfQuestions = 100;
        } else {
            numberOfQuestions = nrOfQuestionsPerPlayer * nrOfPlayers;
        }

        selectedCategory = CategoryActivity.getSelectedCategory().toUpperCase();

        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        listOfPlayers.add(player4);
        listOfPlayers.add(player5);

        Question bigbang = new Question(-5000, "Big Bang!");
        Question ragnarok = new Question(2212, "Ragnarok!");

        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getWritableDatabase();

        cursor = dbHelper.getQuestion(db, selectedCategory, numberOfQuestions);

        if (cursor.moveToFirst()) {
            do {
                String question;
                Integer year;
                question = cursor.getString(1);
                year = cursor.getInt(2);
                Question yearB = new Question(year, question);
                yearlist.add(yearB);

            } while (cursor.moveToNext());
        }
        playedYears.add(bigbang);
        playedYears.add(ragnarok);
    }

    public void startGame() {
        gv.loadPlayerViews();
        printCards();
    }


    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    public Player getPlayer(int nr) {
        return listOfPlayers.get(nr);
    }

    public int getActivePlayerNr() {
        return activePlayerNr;
    }

    private void printCards() {
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

    /*
    Method is called when a year is selected in the timeline. The method controls that only two cards
    are marked at the same time, and that only two adjacent cards can be marked. When two adjacent
    cards are marked, the place card button is enabled
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void clickedYear(View view) {

        GameCard gc = (GameCard) view;

        Question tempYear = null;
        for (Question y : playedYears) {
            if (y.hashCode() == gc.getId()) {
                tempYear = y;
                break;
            }
        }

        if (firstSelectedYear == null && tempYear != secondSelectedYear) {
            if (secondSelectedYear == null) {
                gc.setState("MARKED");
                firstSelectedYear = tempYear;
                firstSelectedButton = gc;
            } else if (isBeside(tempYear, secondSelectedYear)) {
                gc.setState("MARKED");
                firstSelectedYear = tempYear;
                firstSelectedButton = gc;
                gv.answerButton.setEnabled(true);
            }
        } else if (secondSelectedYear == null && tempYear != firstSelectedYear) {
            if (firstSelectedYear == null) {
                gc.setState("MARKED");
                secondSelectedYear = tempYear;
                secondSelectedButton = gc;
            } else if (isBeside(tempYear, firstSelectedYear)) {
                gc.setState("MARKED");
                secondSelectedYear = tempYear;
                secondSelectedButton = gc;
                gv.answerButton.setEnabled(true);
            }
        } else if (tempYear == firstSelectedYear) {
            gc.setState("NORMAL");
            firstSelectedYear = null;
            gv.answerButton.setEnabled(false);
        } else if (tempYear == secondSelectedYear) {
            gc.setState("NORMAL");
            secondSelectedYear = null;
            gv.answerButton.setEnabled(false);
        }
    }

    /*
    Method checks if two selected cards are adjacent in the timeline
     */
    private boolean isBeside(Question year1, Question year2) {

        int i = playedYears.indexOf(year1);
        int j = playedYears.indexOf(year2);

        if (Math.abs(i - j) > 1) {
            return false;
        } else return true;
    }


    private void newQuestion() {
        if (!yearlist.isEmpty()) {
            currentQuestion = yearlist.get(0);
            yearlist.remove(0);
            playedYears.add(currentQuestion);
            gv.question.setText(currentQuestion.getQuestion());
            gv.question.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            gameOver();
        }
    }

    private void gameOver() {

        gv.answerButton.setText("New Game");
        gameOver = true;
        gv.answerButton.setEnabled(true);

        //Make the timeline buttons unclickable and unmark them
        for (int i = 0; i < gv.layout.getChildCount(); ++i) {
            GameCard gameCard = (GameCard) gv.layout.getChildAt(i);
            gameCard.setState("NORMAL");
            gameCard.setClickable(false);
        }

        if (nrOfPlayers != 1) {
            showWinner();
        } else {

            showSinglePlayerResult();
        }

        if (nrOfPlayers == 1 && selectedCategory.equals("ALL CATEGORIES")) {
            checkIfHighScore();
            //only adds highScore if single-player game and all categories are selected
        }
    }

    private void showSinglePlayerResult() {
        gv.question.setText("Game Over. You got " + listOfPlayers.get(activePlayerNr - 1).getScore() + " points");
        gv.lives_nr.setText("X_X");
    }

    private void showWinner() {

        int highestScore = listOfPlayers.get(1).getScore();
        Player winner = null;
        for (int i = 0; i < nrOfPlayers; i++) {
            Player p = listOfPlayers.get(i);
            if (p.getScore() >= highestScore) {
                highestScore = p.getScore();
                winner = p;
            }
        }

        String gameWinners = "";
        int nrOfWinners = 0;
        for (int x = 0; x < nrOfPlayers; x++) {
            if (winner.getScore() == listOfPlayers.get(x).getScore()) {
                gameWinners = gameWinners + listOfPlayers.get(x).getName() + " ";
                nrOfWinners++;
            }
        }

        if (nrOfWinners == 1)
            gv.question.setText("Congratulations! " + gameWinners + "is the winner!");
        else
            gv.question.setText("It's a tie! The winners are: \n" + gameWinners);
        gv.question.setTextColor(Color.parseColor("#699446"));

    }

    private void checkIfHighScore() {
        //only the top 4 scores are saved in the database
        if (player1.getScore() > 0 && dbHelper.getNumberOfHighScores(db) < 4) {
            addHighScore();
        } else if (player1.getScore() > dbHelper.getLowestScore(db)) {
            dbHelper.deleteHighScore(db);
            addHighScore();
        }
    }

    private void addHighScore() {

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

    }

    //changes  player in multi-player games
    private void nextTurn() {

        gv.textViewArrayListPlayers.get(activePlayerNr - 1).setText("Player " + (activePlayerNr) + ":");
        gv.textViewArrayListPlayers.get(activePlayerNr - 1).setTextColor(-1);


        if (activePlayerNr < nrOfPlayers) {
            activePlayerNr++;
        } else {
            activePlayerNr = 1;
        }

        gv.textViewArrayListPlayers.get(activePlayerNr - 1).setText("PLAYER " + (activePlayerNr) + ":");
        gv.textViewArrayListPlayers.get(activePlayerNr - 1).setTextColor(Color.parseColor("#699446"));

    }

    /*
    Method for checking if the given answer is correct, and distributing points and deducting lifes
    accordingly
     */

    public void checkIfCorrect() {

        if (gameOver == false) {
            int i = Math.max(firstSelectedYear.getYear(), secondSelectedYear.getYear());
            int j = Math.min(firstSelectedYear.getYear(), secondSelectedYear.getYear());

            //correct answer if the year for currentQuestion lies between the two choosen years
            if (currentQuestion.getYear() <= i && currentQuestion.getYear() >= j) {

                //Set score
                listOfPlayers.get(activePlayerNr - 1).setScore(1);
                gv.textViewArrayListScore.get(activePlayerNr - 1).setTextColor(-1);
                gv.textViewArrayListScore.get(activePlayerNr - 1).setText(String.valueOf(listOfPlayers.get(activePlayerNr - 1).getScore()) + " p");
                gv.messageBar.setText("");
                printCards();
                nextTurn();
            }

            //answer is wrong
            else {
                //single-player
                if (nrOfPlayers == 1) {
                    listOfPlayers.get(activePlayerNr - 1).looseALife();

                    if ((listOfPlayers.get(activePlayerNr - 1).getLives()) == 0) {
                        gv.messageBar.setText("Wrong, it occured in " + currentQuestion.getYear() + " " + currentQuestion.getYearLabel() + ".");
                        printCards();
                        gameOver();
                    } else {
                        gv.lives_nr.setText(String.valueOf(listOfPlayers.get(activePlayerNr - 1).getLives()));
                        gv.textViewArrayListScore.get(activePlayerNr - 1).setText(String.valueOf(listOfPlayers.get(activePlayerNr - 1).getScore()) + " p");
                        firstSelectedYear = null;
                        secondSelectedYear = null;
                        gv.answerButton.setEnabled(false);
                        gv.messageBar.setText("Wrong, it occured in " + currentQuestion.getYear() + " " + currentQuestion.getYearLabel() + ".");


                        printCards();


                    }

                }
                //multiplayer
                else {
                    gv.textViewArrayListScore.get(activePlayerNr - 1).setTextColor(-65536);
                    gv.messageBar.setText("Wrong, try again!");
                    listOfPlayers.get(activePlayerNr - 1).setScore(-1);
                    gv.textViewArrayListScore.get(activePlayerNr - 1).setText(String.valueOf(listOfPlayers.get(activePlayerNr - 1).getScore()) + " p");


                    //unmark all cards
                    for (int k = 0; k < gv.layout.getChildCount(); ++k) {
                        GameCard gameCard = (GameCard) gv.layout.getChildAt(k);
                        gameCard.setState("NORMAL");
                    }

                    //mark the two wrong cards
                    firstSelectedButton.setState("WRONG");
                    secondSelectedButton.setState("WRONG");

                    firstSelectedYear = null;
                    secondSelectedYear = null;
                    gv.answerButton.setEnabled(false);
                }

            }
        } else {
            Intent i = new Intent(context, GameActivity.class);
            context.startActivity(i);
        }
    }
}
