package bodega.timeline_alfa;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by theYellowBird and victornyden on 2015-05-01.
 */
public class TimelineDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QUESTIONS.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + TimelineTables.Questions.TABLE_NAME+" ("+
             TimelineTables.Questions.COL_CATEGORY+" TEXT,"+ TimelineTables.Questions.COL_QUESTION+" TEXT,"+
             TimelineTables.Questions.COL_YEAR+" INTEGER);";
    private static final String CREATE_QUERY2 = "CREATE TABLE " + TimelineTables.HighScore.TABLE_NAME+" ("+
             TimelineTables.HighScore.COL_SCORE+" INTEGER," + TimelineTables.HighScore.TABLE_NAME+" TEXT);";
    Context context;


    public TimelineDbHelper (Context context){
        super (context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "DATABASE CREATED / OPENED");
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Question table created...");

    db.execSQL(CREATE_QUERY2);
        Log.e("DATABASE OPERATIONS", "HighScore table created...");

    addQuestion("History", "Andra världskriget är igång", 1942 , db);
   // addQuestion("History", "Andra världskriget avslutas", 1945 , db);

    }

    public void addQuestion (String category, String question, Integer year, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TimelineTables.Questions.COL_CATEGORY, category);
        contentValues.put(TimelineTables.Questions.COL_QUESTION, question);
        contentValues.put(TimelineTables.Questions.COL_YEAR, year);
        db.insert(TimelineTables.Questions.TABLE_NAME, null, contentValues);
            Log.e("DATABASE OPERATIONS", "One question row inserted");
    }

    public void addHighScore (Integer highScore, String player, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TimelineTables.HighScore.COL_SCORE, highScore);
        contentValues.put(TimelineTables.HighScore.COL_NAME, player);
        db.insert(TimelineTables.Questions.TABLE_NAME, null, contentValues);
            Log.e("DATABASE OPERATIONS", "One high score row inserted");
    }

    public Cursor getQuestion (SQLiteDatabase db) {

        Cursor cursor;
        String[] projections = {TimelineTables.Questions.COL_CATEGORY, TimelineTables.Questions.COL_QUESTION,
            TimelineTables.Questions.COL_YEAR};
        cursor = db.query(TimelineTables.Questions.TABLE_NAME, projections, null, null,null,null,null);
        return cursor;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
