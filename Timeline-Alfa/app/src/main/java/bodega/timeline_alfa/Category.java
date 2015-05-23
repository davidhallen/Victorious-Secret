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


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category1)).getText();
                Drawable drawable1Clicked = res. getDrawable(R.drawable.geopolitics_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.geopolitics);
                    button1.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button1;
                    lastClickedNormalBackground = findViewById(R.id.Category1).getBackground();
                    button1.setBackground(drawable1Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category1).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button1;
                    button1.setBackground(drawable1Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category2)).getText();
                Drawable drawable2Clicked = res. getDrawable(R.drawable.literature_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.literature);
                    button2.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button2;
                    lastClickedNormalBackground = findViewById(R.id.Category2).getBackground();
                    button2.setBackground(drawable2Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category2).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button2;
                    button2.setBackground(drawable2Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category3)).getText();
                Drawable drawable3Clicked = res. getDrawable(R.drawable.architecture_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.architecture);
                    button3.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button3;
                    lastClickedNormalBackground = findViewById(R.id.Category3).getBackground();
                    button3.setBackground(drawable3Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category3).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button3;
                    button3.setBackground(drawable3Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category4)).getText();
                Drawable drawable4Clicked = res. getDrawable(R.drawable.wars_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.wars);
                    button4.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button4;
                    lastClickedNormalBackground = findViewById(R.id.Category4).getBackground();
                    button4.setBackground(drawable4Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category4).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button4;
                    button4.setBackground(drawable4Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category5)).getText();
                Drawable drawable5Clicked = res. getDrawable(R.drawable.inventions_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.inventions);
                    button5.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button5;
                    lastClickedNormalBackground = findViewById(R.id.Category5).getBackground();
                    button5.setBackground(drawable5Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category5).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button5;
                    button5.setBackground(drawable5Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category6)).getText();
                Drawable drawable6Clicked = res. getDrawable(R.drawable.kings_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.kings);
                    button6.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button6;
                    lastClickedNormalBackground = findViewById(R.id.Category6).getBackground();
                    button6.setBackground(drawable6Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category6).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button6;
                    button6.setBackground(drawable6Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category7)).getText();
                Drawable drawable7Clicked = res. getDrawable(R.drawable.music_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.music);
                    button7.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button7;
                    lastClickedNormalBackground = findViewById(R.id.Category7).getBackground();
                    button7.setBackground(drawable7Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category7).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button7;
                    button7.setBackground(drawable7Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category8)).getText();
                Drawable drawable8Clicked = res. getDrawable(R.drawable.ancient_history_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.ancient_history);
                    button8.setBackground(temp);
                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button8;
                    lastClickedNormalBackground = findViewById(R.id.Category8).getBackground();
                    button8.setBackground(drawable8Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category8).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button8;
                    button8.setBackground(drawable8Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }

            }

        });
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category9)).getText();
                Drawable drawable9Clicked = res. getDrawable(R.drawable.enlightenment_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.enlightenment);
                    button9.setBackground(temp);
                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button9;
                    lastClickedNormalBackground = findViewById(R.id.Category9).getBackground();
                    button9.setBackground(drawable9Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category9).getBackground();

                    if (lastClickedCategoryButton == button11) {
                        button11.setText("Custom Categories");
                    }

                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button9;
                    button9.setBackground(drawable9Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }

            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String buttonText = (String) ((Button) findViewById(R.id.Category10)).getText();
                Drawable drawable10Clicked = res. getDrawable(R.drawable.the_industrial_revolution_clicked);
                if (getSelectedCategory().equals(buttonText)){
                    setSelectedCategory("NOSELECTEDCATEGORY");
                    Drawable temp = res. getDrawable(R.drawable.the_industrial_revolution);
                    button10.setBackground(temp);

                }
                else if (lastClickedCategoryButton==null){
                    setSelectedCategory(buttonText);
                    lastClickedCategoryButton = button10;
                    lastClickedNormalBackground = findViewById(R.id.Category10).getBackground();
                    button10.setBackground(drawable10Clicked);

                }
                else{
                    Drawable tempBackground = (Drawable) findViewById(R.id.Category10).getBackground();
                    lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                    lastClickedCategoryButton = button10;
                    button10.setBackground(drawable10Clicked);
                    lastClickedNormalBackground = tempBackground;
                    setSelectedCategory(buttonText);
                }


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

                        if (lastClickedCategoryButton==null){
                            lastClickedCategoryButton = button11;
                            lastClickedNormalBackground = findViewById(R.id.Category11).getBackground();
                            button11.setBackground(drawable11Clicked);

                        }
                        else{
                            Drawable tempBackground = (Drawable) findViewById(R.id.Category11).getBackground();
                            lastClickedCategoryButton.setBackground(lastClickedNormalBackground);
                            lastClickedCategoryButton = button11;
                            button11.setBackground(drawable11Clicked);
                            lastClickedNormalBackground = tempBackground;

                        }



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