package bodega.timeline_alfa;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
      }

    };


    public void newButton (View view) {

        if (gameOver == false) {
            int i = Math.max(firstSelectedYear.getYear(), secondSelectedYear.getYear());
            int j = Math.min(firstSelectedYear.getYear(), secondSelectedYear.getYear());

            if (currentQuestion.getYear() <= i && currentQuestion.getYear() >= j) {
                printButtons();
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


}


