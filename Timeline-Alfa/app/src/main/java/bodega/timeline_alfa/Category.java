package bodega.timeline_alfa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class Category extends ActionBarActivity {

    boolean category1;
    boolean category2;
    boolean category3;
    boolean category4;
    boolean category5;
    boolean category6;
    boolean category7;
    boolean category8;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;

    private Button playButton;
    private Button backToSetUpButton;
    private Button timeSpanButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        button1 = (Button) findViewById(R.id.Category1);
        button2 = (Button) findViewById(R.id.Category2);
        button3 = (Button) findViewById(R.id.Category3);
        button4 = (Button) findViewById(R.id.Category4);
        button5 = (Button) findViewById(R.id.Category5);
        button6 = (Button) findViewById(R.id.Category6);
        button7 = (Button) findViewById(R.id.Category7);
        button8 = (Button) findViewById(R.id.Category8);

        playButton = (Button) findViewById(R.id.PlayCategory);
        backToSetUpButton = (Button) findViewById(R.id.BackFromCategory);
        timeSpanButton = (Button) findViewById(R.id.ChooseTimeSpan);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category1=true){
                    category1=false;
                    String pathname="/Timeline-Alfa/app/src/main/res/drawable/delete.ico";
                    findViewById(R.id.Category1check).setBackground(Drawable.createFromPath(pathname));

                }
                else
                    category1=true;

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category2=true){
                    category2=false;

                }
                else
                    category2=true;

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category3=true){
                    category3=false;

                }
                else
                    category3=true;

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category4=true){
                    category4=false;

                }
                else
                    category4=true;

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category5=true){
                    category5=false;

                }
                else
                    category5=true;


            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category6=true){
                    category6=false;

                }
                else
                    category6=true;

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category7=true){
                    category7=false;

                }
                else
                    category7=true;
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (category8=true){
                    category8=false;

                }
                else
                    category8=true;
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,MainActivity.class));
            }
        });
        backToSetUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,Setup.class));

            }
        });
        timeSpanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,TimeSpan.class));
            }
        });


    }

}