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

    private static String selectedCategory = "NOSELECTEDCATEGORY";

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;

    private Button playButton;
    private Button backButton;



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
        button9 = (Button) findViewById(R.id.Category9);
        button10 = (Button) findViewById(R.id.Category10);

        playButton = (Button) findViewById(R.id.CategoryPlay);
        backButton = (Button) findViewById(R.id.CategoryBack);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category1)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category2)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category3)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category4)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category5)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category6)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category7)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category8)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);


            }

        });
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category9)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category10)).getText();
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                }
                else
                    setSelectedCategory(buttonText);

            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,MainActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,PlayersMenu.class));

            }
        });



    }
    public void setSelectedCategory(String category) {
        selectedCategory = category;
    }

    public static String getSelectedCategory() {
        return selectedCategory;
    }

}