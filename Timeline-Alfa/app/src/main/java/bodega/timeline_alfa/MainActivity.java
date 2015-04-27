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
    private ArrayList <yearButton> yearlist = new ArrayList <yearButton> (); //for yearButtons not yet played
    private ArrayList <yearButton> playedYears = new ArrayList <yearButton> (); //for played yearButtons
    private yearButton currentQuestion;
    private yearButton firstSelectedYear;
    private yearButton secondSelectedYear;

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
       layout = (LinearLayout) findViewById(R.id.timelineLayout);
       question = (TextView) findViewById(R.id.question);
       yearButton bigbang = new yearButton (-5000, "Biggie Bang Bong");
       yearButton ragnarok = new yearButton (2212, "Ragnarok!");
       yearButton y1 = new yearButton (1912, "När var OS i Stockholm?");
       yearButton y2 = new yearButton (1986, "När dog Palme?");
       yearButton y3 = new yearButton (1492, "När upptäckte Columbus Amerika?");
       yearButton y4 = new yearButton (0, "När föddes Jesus?");
       yearlist.add(y1); yearlist.add(y2); yearlist.add(y3); yearlist.add(y4);
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
          question.setText("Slut på frågor mannen");
      }

    };

    public void newButton (View view){

        int i = Math.max(firstSelectedYear.getYear(), secondSelectedYear.getYear());
        int j = Math.min(firstSelectedYear.getYear(), secondSelectedYear.getYear());

        if (currentQuestion.getYear() <= i && currentQuestion.getYear()>= j){
            printButtons();
        }

        else {
            question.setText("Wrong answer, yo");
        }

    }




    public void printButtons(){
        layout.removeAllViews();
        firstSelectedYear = null;
        secondSelectedYear = null;

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(300,500);
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


    public void clickedYear(View view) {

        yearButton tempYear = null;
        for (yearButton y : playedYears) {
            if (y.hashCode() == view.getId()) {
                tempYear = y;
                question.setText("" + y.getYear());
                break;
            }
        }

        if (tempYear == firstSelectedYear){
            firstSelectedYear = null;
            view.setBackgroundColor(Color.BLUE);

            if (secondSelectedYear != null){
                firstSelectedYear = new yearButton (secondSelectedYear.getYear(), secondSelectedYear.getQuestion());
                secondSelectedYear = null;
            }
        }

        else if (tempYear == secondSelectedYear) {
            secondSelectedYear = null;
            view.setBackgroundColor(Color.BLUE);
        }

        else if (secondSelectedYear != null) {
            //doNothing
        }

        else if (firstSelectedYear == null){
            view.setBackgroundColor(Color.RED);
            firstSelectedYear = tempYear;
        }

        else {
            if (isBeside(tempYear)){
                view.setBackgroundColor(Color.RED);
                secondSelectedYear = tempYear;
            }


        }

    }

    public boolean isBeside(yearButton year){

        int i = playedYears.indexOf(year);
        int j = playedYears.indexOf(firstSelectedYear);

        if (Math.abs(i-j) > 1){
            return false;
        }

        else return true;

    }


}

