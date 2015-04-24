package com.example.victornyden.basictimeline;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Integer firstYear = null;
    private Integer secondYear = null;
    private int thisYear;
    private Button yearButton;
    private Button placeCardButton;
    private Integer [] years;
    private ArrayList <Integer> playedYears = new ArrayList <> ();


    /*private TextView questionView;
    private TextView answerView;
    private EditText answerText;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        years = new Integer [] {3,10,11,23,45};
        playedYears.add(0); playedYears.add(100);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void yearButtonPress (View view) {
        String yS;
        yS = ((Button) view).getText().toString();
        int y = Integer.parseInt(yS);

        if (firstYear == y){
            firstYear = null;
            //changeColor back to normal
        }

        else if (secondYear == y) {
            secondYear = null;
            //changeColor back to normal
        }


        else if (firstYear == null) {
            firstYear = y;

        }

        else if (secondYear == null) {
            secondYear = y;
        }

        if (firstYear != null && secondYear != null){

               Collections.sort(playedYears);
               int i = playedYears.indexOf(firstYear);
               int j = playedYears.indexOf(secondYear);

               if (Math.abs(i-j)>1){

                   // felmedelande


               }
                else {
                   activatePlaceCardButton();


                }

        }

        /*
        kolla om firstYear är tom, om så placera i firstYear
        ändra färg på tryckt kort

        om firstYear ej är tom
           - om == firstYear --> töm firstYear, sätt orginalfärg
           - om != firstYear
                    - kolla att firstYear ligger bredvid tryckt kort
                        - om false --> felmedelande
                        - om true --> placera tryckt årtal i secondYear, ändra färg och activatePlaceCardButton






         */

    }


    public void activatePlaceCardButton () {

        /*
        aktivera knappen

         */


    }


    public void newQuestion () {

        /*
        ändra text till ny fråga och disable knappen


        */

    }


    public void placeCard() {

        int earlyYear = Math.min(firstYear,secondYear);
        int lateYear = Math.max (firstYear, secondYear);

        if (earlyYear <= thisYear && thisYear <= lateYear){

            //placeNewCard på gridden
            // change question to next question
            // enable nextQuestion button

        }


        /*
        sätt max av firstYear och secondYear till lateYear
        sätt min av firstYear och secondYear till earlyYear

        kolla att earlyYear <= thisYear <= lateYear

        if true
            increaseScore ()
            feedback()
            createNewCard ()

         if false
            feedback()
            decreaseScore()
            checkGameOver()

          enable questionButton
          change text on button to next Question


         */


    }

}
