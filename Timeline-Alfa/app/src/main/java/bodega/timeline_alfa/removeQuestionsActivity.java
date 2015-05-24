package bodega.timeline_alfa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class removeQuestionsActivity extends ActionBarActivity {

    Context context = this;
    TimelineDbHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayAdapter<String> adapter;
    ArrayList<String> categories = new ArrayList <> ();
    Button catButton;
    String selectedCategory;
    LinearLayout questions;
    TextView title;
    String question;
    Integer year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_questions);
        dbHelper = new TimelineDbHelper(context);
        catButton = (Button)findViewById(R.id.button);
        questions = (LinearLayout)findViewById(R.id.questionList);
        title = (TextView)findViewById(R.id.removeTitle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remove_questions, menu);
        return true;
    }


    public void getCategories(View view){

        categories.clear();

        db = dbHelper.getReadableDatabase();

        cursor = dbHelper.getCustomCategories(db);

        if (cursor.moveToFirst()){
            do {
                String category;
                category = cursor.getString(0);
                categories.add(category);
            } while (cursor.moveToNext());
        }



        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        new AlertDialog.Builder(this)
                .setTitle("Choose Category")

                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category = categories.get(which);
                        catButton.setText(category);
                        dialog.dismiss();
                        selectedCategory = category;
                        showAddedQuestions();
                    }
                }).create().show();
    }

    private void showAddedQuestions (){

        questions.removeAllViews();
        db = dbHelper.getWritableDatabase();
        cursor = dbHelper.getAddedQuestions(selectedCategory, db);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = 20;

        if (cursor.moveToFirst()){
            do {
                title.setHint("Click on a question to remove it");
                String question;
                Integer year;
                question = cursor.getString(1);
                year = cursor.getInt(2);
                Button but = new Button (this);
                but.setText(question + "\n Year: " + year);
                but.setBackgroundResource(R.drawable.scorefields);
                but.setLayoutParams(params);
                but.setWidth(500);
                but.setTextSize(15);
                but.setTag(R.id.buttonId1,question);
                but.setTag(R.id.buttonId2,year);
                questions.addView(but);
                but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        removeQuestion(v.getTag(R.id.buttonId1), v.getTag(R.id.buttonId2));
                    }
                } );
                }
                while (cursor.moveToNext());
        }
        else {
            title.setHint("Remove Added Questions");
            catButton.setText("Choose Category");

        }

    }

    private void removeQuestion (Object q, Object y){

            question = (String) q;
            year = (Integer)y;


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Remove this question?" + "\n" + question + ":" + year );


            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeQuestion();
                    dialog.cancel();
                }
            });
            builder.show();
    }

    public void back (View v) {

        startActivity(new Intent(removeQuestionsActivity.this,QuestionAdder.class));

    }

    private void removeQuestion(){

        dbHelper.deleteQuestion(selectedCategory,question,year,db);
        showAddedQuestions();

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
}
