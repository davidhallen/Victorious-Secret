package victorioussecret.timeline_beta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class QuestionAdderActivity extends ActionBarActivity {

    Context context = this;
    TimelineDbHelper dbHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;
    Cursor cursor;
    ArrayAdapter<String> adapter;
    ArrayList<String> categories = new ArrayList <> ();
    TextView chosenCategory;
    TextView question;
    TextView year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_adder);
        chosenCategory = (TextView)findViewById(R.id.categoryChoice);
        question = (TextView)findViewById(R.id.question);
        year = (TextView)findViewById(R.id.year);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dbHelper = new TimelineDbHelper(context);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_adder, menu);
        return true;
    }

    /*
    Fetches all categories from the database and presents them in a pop up window.
     */
    public void chooseCategory (View view){

        categories.clear();

        dbRead = dbHelper.getReadableDatabase();

        cursor = dbHelper.getAllCategories(dbRead);

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
                        chosenCategory.setText(category);
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /*
    Adds a question to the database. Checks so that the question and category textViews are not empty
    and that the chosen year lies between -5000 <= X <= 2212.
     */

    public void addNewQuestion (View view){
        String message;
        String c = chosenCategory.getText().toString().trim().toUpperCase();
        String q = question.getText().toString().trim().toUpperCase();
        String checkEmpty = year.getText().toString().trim();
        Integer y;
        dbWrite = dbHelper.getWritableDatabase();

        if(checkEmpty.equals("")){
            y = -5001;
        }
        else {
            y = Integer.parseInt(checkEmpty);
        }

        if (!c.equals("")  && !q.equals("") && 2112>=y && y >= -5000){

            dbHelper.addQuestion(c,q,y,0,dbWrite);
            question.setText(null);
            year.setText(null);
            message = "Question added!";
            popUpMessage(message);
        }

        else if (c.equals("")){
            message = "Please choose a category";
            popUpMessage(message);
        }

        else if(q.equals("")){
            message = "Please write a question";
            popUpMessage(message);
        }

        else if (y>=2112 || y<= -5000){
            message = "Please choose a year between 5000 BC and 2112 AD";
            popUpMessage(message);
        }
    }

    private void popUpMessage (String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mes);

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void backToExtras (View view) {
        startActivity(new Intent(QuestionAdderActivity.this,ExtrasActivity.class));
    }

    public void removeAddedQuestions(View view){

        startActivity(new Intent(QuestionAdderActivity.this,RemoveQuestionsActivity.class));
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