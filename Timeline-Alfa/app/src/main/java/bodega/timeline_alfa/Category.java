package bodega.timeline_alfa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


public class Category extends ActionBarActivity {

    private static String selectedCategory = "ALL CATEGORIES";

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
    private Button button11;


    private Button playButton;
    private Button backButton;

    private Button lastClickedCategoryButton;
    private Drawable lastClickedNormalBackground = null;

    Context context = this;
    ArrayList<String> categories = new ArrayList <> ();
    LinearLayout linLay;


    private Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        res = getResources();

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
        button11 = (Button) findViewById(R.id.Category11);

        playButton = (Button) findViewById(R.id.CategoryPlay);
        backButton = (Button) findViewById(R.id.CategoryBack);
        linLay = (LinearLayout) findViewById(R.id.CategoryLayout1);

        Drawable drawable1Clicked = res. getDrawable(R.drawable.geopolitics_clicked);
        lastClickedCategoryButton = button1;
        lastClickedNormalBackground = button1.getBackground();
        button1.setBackground(drawable1Clicked);
        button1.setClickable(false);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable1Clicked = res. getDrawable(R.drawable.geopolitics_clicked);
                updateButtons(button1,drawable1Clicked);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable2Clicked = res. getDrawable(R.drawable.literature_clicked);
                updateButtons(button2,drawable2Clicked);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable3Clicked = res. getDrawable(R.drawable.architecture_clicked);
                updateButtons(button3,drawable3Clicked);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable4Clicked = res. getDrawable(R.drawable.wars_clicked);
                updateButtons(button4,drawable4Clicked);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable5Clicked = res. getDrawable(R.drawable.inventions_clicked);
                updateButtons(button5,drawable5Clicked);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable6Clicked = res. getDrawable(R.drawable.kings_clicked);
                updateButtons(button6,drawable6Clicked);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable7Clicked = res. getDrawable(R.drawable.music_clicked);
                updateButtons(button7,drawable7Clicked);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable8Clicked = res. getDrawable(R.drawable.ancient_history_clicked);
                updateButtons(button8,drawable8Clicked);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable9Clicked = res. getDrawable(R.drawable.enlightenment_clicked);
                updateButtons(button9,drawable9Clicked);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Drawable drawable10Clicked = res.getDrawable(R.drawable.the_industrial_revolution_clicked);
                updateButtons(button10,drawable10Clicked);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,GameActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Category.this,PlayersMenu.class));

            }
        });

    }

    private void updateButtons(Button but, Drawable clicked){
        Drawable tempBackground = (Drawable) but.getBackground();

        if (lastClickedCategoryButton == button11 && but != button11) {
            button11.setText("Custom Categories");
        }

        if (lastClickedCategoryButton != button11 || but != button11) {
            lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
            lastClickedCategoryButton.setClickable(true);
            lastClickedNormalBackground = tempBackground;
        }

        if (but != button11){
            but.setClickable(false);
        }

        lastClickedCategoryButton = but;
        but.setBackground(clicked);
        setSelectedCategory(but.getText().toString().toUpperCase());

    }


    public void getCustomCategories(View view){

        TimelineDbHelper dbHelper = new TimelineDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = dbHelper.getAllCategories(db);


        if (cursor.moveToFirst()){
            do {
                String category;
                category = cursor.getString(0);
                categories.add(category);
            } while (cursor.moveToNext());
        }

        Set<String> s = new LinkedHashSet<String>(categories);
        categories.clear();
        categories.addAll(s);
        s.clear();

        for(int i=0; i< linLay.getChildCount(); ++i) {
            Button nextChild = (Button)linLay.getChildAt(i);
            s.add(nextChild.getText().toString().toUpperCase());
        }

        categories.removeAll(s);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        new AlertDialog.Builder(this)
                .setTitle("Choose Category")

                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category = categories.get(which);
                        button11.setText(category);
                        dialog.dismiss();
                        setSelectedCategory(category);
                        Drawable drawable11Clicked = res. getDrawable(R.drawable.the_industrial_revolution_clicked);
                        updateButtons(button11, drawable11Clicked);
                    }
                }).create().show();
    }



    public void setSelectedCategory(String category) {
        selectedCategory = category;
    }

    public static String getSelectedCategory() {
        return selectedCategory;
    }

    public int hashCode(){
        return super.hashCode();
    }
    public boolean equals(Object obj){

        return true;
    }


}