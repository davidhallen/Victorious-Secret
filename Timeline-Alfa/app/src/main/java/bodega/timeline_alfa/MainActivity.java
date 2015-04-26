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
    private int y = 0;
    private TextView question;
    private int lol = 0;
    private ArrayList <yearButton> yearlist = new ArrayList <yearButton> ();
    private ArrayList <yearButton> playedYears = new ArrayList <yearButton> ();
    private yearButton currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        final Button BigBang = (Button) findViewById(R.id.BigBang);
        final Button Ragnarok = (Button) findViewById(R.id.Ragnarok);
        layout = (LinearLayout) findViewById(R.id.timelineLayout);
        question = (TextView) findViewById(R.id.question);

        BigBang.setOnClickListener(new View.OnClickListener() {
            //yearButton y1 = new yearButton(1);
            int clickedButton= 10;
            @Override
            public void onClick(View view){

                if(clickedButton%2 != 0) {
                    BigBang.setBackgroundColor(Color.rgb(0, 155, 0));
                    clickedButton++;
                }

                else{
                    BigBang.setBackgroundColor(Color.rgb(155, 0, 0));
                    clickedButton++;
                }

                
                 }
        });






        Ragnarok.setOnClickListener(new View.OnClickListener() {
            //yearButton y1 = new yearButton(1);
            int clickedButton= 10;
            @Override
            public void onClick(View view){

                if(clickedButton%2 != 0) {
                    Ragnarok.setBackgroundColor(Color.rgb(0, 155, 0));
                    clickedButton++;
                }

                else{
                    Ragnarok.setBackgroundColor(Color.rgb(155, 0, 0));
                    clickedButton++;
                }
            }

        });

        init();

    }

    public void init(){

       yearButton y1 = new yearButton (1912, "När var OS i Stockholm?");
       yearButton y2 = new yearButton (1986, "När dog Palme?");
       yearButton y3 = new yearButton (1492, "När upptäckte Columbus Amerika?");
       yearButton y4 = new yearButton (0, "När föddes Jesus?");
       yearlist.add(y1); yearlist.add(y2); yearlist.add(y3); yearlist.add(y4);
       Collections.shuffle(yearlist);
       newQuestion();
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

    public void newButton(View view){
        layout.removeAllViews();
        y++;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(300,500);
        layoutParams.setMargins(40,0,0,0);


        Button bigbang = new Button (this);
        bigbang.setText("BigBang");
        bigbang.setBackgroundColor(Color.BLUE);
        bigbang.setLayoutParams(layoutParams);
        bigbang.setTag("bigbang");
        bigbang.setClickable(true);
        bigbang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                clickedYear();
            }
        });

        layout.addView(bigbang);

        Collections.sort(playedYears);

        if (!playedYears.isEmpty()) {
            for (int x = 0; x < playedYears.size(); x++) {
                Button year = new Button(this);
                //String s = Integer.toString(x);
                int s = playedYears.get(x).getYear();
                year.setText(""+s);
                //year.setText(s);
                year.setBackgroundColor(Color.BLUE);
                year.setLayoutParams(layoutParams);
                bigbang.setClickable(true);
                year.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        clickedYear();
                    }
                });
                layout.addView(year);

            }
        }
        Button ragnarok = new Button (this);
        ragnarok.setText("ragnarok");
        ragnarok.setBackgroundColor(Color.BLUE);
        ragnarok.setLayoutParams(layoutParams);
        bigbang.setClickable(true);
        ragnarok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickedYear();

            }
        });
        layout.addView(ragnarok);

        newQuestion();

    }


    public void clickedYear(){
        question.setText("Du har klickat på en knapp" + lol);
        lol++;
    }
}

