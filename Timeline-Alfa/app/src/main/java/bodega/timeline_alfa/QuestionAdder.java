package bodega.timeline_alfa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class QuestionAdder extends ActionBarActivity {

    Context context = this;
    TimelineDbHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayAdapter<String> adapter;
    ArrayList<String> categories = new ArrayList <> ();
    TextView chosenCategory;
    TextView question;
    TextView year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_adder);
        chosenCategory = (TextView)findViewById(R.id.categoryChoice);
        question = (TextView)findViewById(R.id.question);
        year = (TextView)findViewById(R.id.year);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_adder, menu);
        return true;
    }

    public void chooseCategory (View view){

        dbHelper = new TimelineDbHelper(context);
        db = dbHelper.getReadableDatabase();

        cursor = dbHelper.getCategories(db);

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

    public void addNewQuestion (View view){
        String message;
        String c = chosenCategory.getText().toString().trim().toUpperCase();
        String q = question.getText().toString().trim().toUpperCase();
        String checkEmpty = year.getText().toString().trim();
        Integer y;
        if(checkEmpty.equals("")){
            y = -5001;
        }
        else {
            y = Integer.parseInt(checkEmpty);
        }

        if (!c.equals("")  && !q.equals("") && 2112>=y && y >= -5000){
            db = dbHelper.getWritableDatabase();
            dbHelper.addQuestion(c,q,y,db);
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

    public void popUpMessage (String mes){
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
