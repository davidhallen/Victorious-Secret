package bodega.timeline_alfa;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by theYellowBird and victornyden on 2015-05-01.
 */
public class TimelineDbHelper extends SQLiteOpenHelper {

    private Integer intKey = 0;
    private static final String DATABASE_NAME = "QUESTIONS.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + TimelineTables.Questions.TABLE_NAME+" ("+
             TimelineTables.Questions.COL_CATEGORY+" TEXT,"+ TimelineTables.Questions.COL_QUESTION+" TEXT,"+
             TimelineTables.Questions.COL_YEAR+" INTEGER);";
    private static final String CREATE_QUERY2 = "CREATE TABLE " + TimelineTables.HighScore.TABLE_NAME+" ("+
             TimelineTables.HighScore.COL_INTKEY+" INTEGER,"+ TimelineTables.HighScore.COL_SCORE +" INTEGER,"
             + TimelineTables.HighScore.COL_NAME+" TEXT);";
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

        XmlResourceParser parser = context.getResources().getXml(R.xml.questions);

        String newQuestion = "";
        Integer newYear = -1;
        String newCategory = "";
        int eventType = -1;
        String currentLevel = "";
        try {
            while (eventType != XmlResourceParser.END_DOCUMENT){

                if (eventType == XmlResourceParser.START_TAG){

                    if(parser.getName().equalsIgnoreCase("category")){
                        currentLevel = "category";
                    }
                    else if(parser.getName().equalsIgnoreCase("question")){
                        currentLevel = "question";
                    }
                    else if(parser.getName().equalsIgnoreCase("year")){
                        currentLevel = "year";
                    }
                }

                else if (eventType == XmlResourceParser.END_TAG){
                    if(parser.getName().equalsIgnoreCase("content")) {
                        addQuestion(newCategory, newQuestion, newYear, db);
                    }
                }

                else if (eventType == XmlResourceParser.TEXT){
                    if (currentLevel.equalsIgnoreCase("category")){
                        newCategory = parser.getText();
                    }
                    if (currentLevel.equalsIgnoreCase("question")){
                        newQuestion = parser.getText();
                    }
                    if (currentLevel.equalsIgnoreCase("year")){
                        String interYear = parser.getText();
                        newYear = Integer.parseInt(interYear);
                    }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            Log.w("Error", "XMLpullError");
             e.printStackTrace();
        }
        catch (java.io.IOException e){
            Log.w("Error", "IOExp");
              e.printStackTrace();
        }
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
        int intK = getHighestHighScoreKeyInTable(db);
        contentValues.put(TimelineTables.HighScore.COL_INTKEY, intK +1);
        contentValues.put(TimelineTables.HighScore.COL_SCORE, highScore);
        contentValues.put(TimelineTables.HighScore.COL_NAME, player);
        db.insert(TimelineTables.HighScore.TABLE_NAME, null, contentValues);
            Log.e("DATABASE OPERATIONS", "One high score row inserted");
    }

    public Cursor getQuestion (SQLiteDatabase db, String category) {
        Cursor cursor;
        String[] projections = {TimelineTables.Questions.COL_CATEGORY, TimelineTables.Questions.COL_QUESTION,
                TimelineTables.Questions.COL_YEAR};

        if (category == "AllCategories") {
            cursor = db.query(TimelineTables.Questions.TABLE_NAME, projections, null, null, null, null, null);
            return cursor;
        }
        else{
            cursor = db.query(TimelineTables.Questions.TABLE_NAME, projections, TimelineTables.Questions.COL_CATEGORY + "=?",
                    new String [] {category},null, null, null);
            return cursor;
        }
    }

    public Cursor getHighScore (SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TimelineTables.HighScore.COL_SCORE, TimelineTables.HighScore.COL_NAME};
        cursor = db.query(TimelineTables.HighScore.TABLE_NAME, projections, null, null, null, null, TimelineTables.HighScore.COL_SCORE +" DESC");
        return cursor;
    }

    public void deleteHighScore (SQLiteDatabase db){
        Integer minScore = getLowestScore(db);
        if (minScore != 0) {
            Cursor c = db.query(TimelineTables.HighScore.TABLE_NAME, new String[]{TimelineTables.HighScore.COL_SCORE, TimelineTables.HighScore.COL_INTKEY},
                    TimelineTables.HighScore.COL_SCORE + "=?", new String[]{String.valueOf(minScore)}, null, null, TimelineTables.HighScore.COL_INTKEY + " DESC");
            c.moveToFirst();
            Integer deleteKey = c.getInt(1);
            db.execSQL("delete from " + TimelineTables.HighScore.TABLE_NAME + " where " + TimelineTables.HighScore.COL_INTKEY + "='" + deleteKey + "'");
        }
    }

    public int getLowestScore(SQLiteDatabase db){
        Cursor c = db.query(TimelineTables.HighScore.TABLE_NAME, new String[] { "min(" + TimelineTables.HighScore.COL_SCORE + ")" }, null, null,
                null, null, null);
        if(!c.moveToFirst()){
            return 0;
        }
        else {
            c.moveToFirst();
            int lowestScore = c.getInt(0);
            return lowestScore;
        }
    }

    public int getHighestHighScoreKeyInTable (SQLiteDatabase db){
        Cursor c = db.query(TimelineTables.HighScore.TABLE_NAME, new String[] { "max(" + TimelineTables.HighScore.COL_INTKEY + ")" }, null, null,
                null, null, null);
        if (!c.moveToFirst()){
            return 0;
        }
        else {
            return c.getInt(0);
        }
    }

    public int getNumberOfHighScores (SQLiteDatabase db) {
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TimelineTables.HighScore.TABLE_NAME);
        return numRows;
    }
/*
        Cursor c = db.query(TimelineTables.HighScore.TABLE_NAME, new String[] {TimelineTables.HighScore.COL_SCORE}, null, null,
                null, null, null);
        if (!c.moveToFirst()){
            return 0;
        }
        else {
            int i = 0;
            do {
                i++;

            }
            while (c.moveToNext());
            return i;
        }
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
