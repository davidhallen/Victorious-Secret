package victorioussecret.timeline_alfa;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
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
             TimelineTables.Questions.COL_YEAR+" INTEGER,"+ TimelineTables.Questions.COL_BOOLEAN+" INTEGER);";
    private static final String CREATE_QUERY2 = "CREATE TABLE " + TimelineTables.HighScore.TABLE_NAME+" ("+
             TimelineTables.HighScore.COL_INTKEY+" INTEGER,"+ TimelineTables.HighScore.COL_SCORE +" INTEGER,"
             + TimelineTables.HighScore.COL_NAME+" TEXT);";
    Context context;


    public TimelineDbHelper (Context context){
        super (context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "DATABASE CREATED / OPENED");
        this.context = context;
    }

    /*
    Creates Question and HighScore tables. Parses questions from xml file named questions
    in res/xml folder and adds these to Questions table.
    */

    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL(CREATE_QUERY);

    db.execSQL(CREATE_QUERY2);

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
                        addQuestion(newCategory, newQuestion, newYear, 1, db);
                    }
                }

                else if (eventType == XmlResourceParser.TEXT){
                    if (currentLevel.equalsIgnoreCase("category")){
                        newCategory = parser.getText().toUpperCase().trim();
                    }
                    if (currentLevel.equalsIgnoreCase("question")){
                        newQuestion = parser.getText();
                    }
                    if (currentLevel.equalsIgnoreCase("year")){
                        String interYear = parser.getText().trim();
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

    /*
    The variable bool is used to decide whether the question should be used during quick-play and
    custom games with all categories selected. bool is set to one for questions parsed from the questions
    xml (these are used in the above mentioned occasions) and is set to zero for user added questions.
    */

    public void addQuestion (String category, String question, Integer year, Integer bool, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TimelineTables.Questions.COL_CATEGORY, category);
        contentValues.put(TimelineTables.Questions.COL_QUESTION, question);
        contentValues.put(TimelineTables.Questions.COL_YEAR, year);
        contentValues.put(TimelineTables.Questions.COL_BOOLEAN, bool);
        db.insert(TimelineTables.Questions.TABLE_NAME, null, contentValues);
    }

    /*
    The newest entry in the high score table has always the highest intKey value of the included
    rows.
     */

    public void addHighScore (Integer highScore, String player, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        int intK = getHighestHighScoreKeyInTable(db);
        contentValues.put(TimelineTables.HighScore.COL_INTKEY, intK + 1);
        contentValues.put(TimelineTables.HighScore.COL_SCORE, highScore);
        contentValues.put(TimelineTables.HighScore.COL_NAME, player);
        db.insert(TimelineTables.HighScore.TABLE_NAME, null, contentValues);
    }

    /*
    If all categories are chosen no user added questions are fetched. This is controlled through
    the BOOLEAN column. Rows with the value 1 in this column are original questions and rows with
    the value 0 are user added questions.
     */

    public Cursor getQuestion (SQLiteDatabase db, String category, int numberOfQuestions) {
        String numQuest = Integer.toString(numberOfQuestions);
        Cursor cursor;
        String[] projections = {TimelineTables.Questions.COL_CATEGORY, TimelineTables.Questions.COL_QUESTION,
                TimelineTables.Questions.COL_YEAR, TimelineTables.Questions.COL_BOOLEAN};

        if (category.equals("ALL CATEGORIES")) {
            cursor = db.query(TimelineTables.Questions.TABLE_NAME, projections, TimelineTables.Questions.COL_BOOLEAN + "=?", new String [] {"1"}, null,null, "RANDOM()", numQuest);
            return cursor;
        }
        else{
            cursor = db.query(TimelineTables.Questions.TABLE_NAME, projections, TimelineTables.Questions.COL_CATEGORY + "=?",
                    new String [] {category},null, null, "RANDOM()", numQuest);
            return cursor;
        }
    }

    public Cursor getHighScore (SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TimelineTables.HighScore.COL_SCORE, TimelineTables.HighScore.COL_NAME};
        cursor = db.query(TimelineTables.HighScore.TABLE_NAME, projections, null, null, null, null, TimelineTables.HighScore.COL_SCORE +" DESC");
        return cursor;
    }

    /*
    If more than one row share the lowest score in the table, the row among them with the highest value in the
    INTKEY column is deleted. This is the row most recently added.
    */

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

    public void deleteQuestion (String category, String question, Integer year, SQLiteDatabase db){
        db.execSQL("delete from " + TimelineTables.Questions.TABLE_NAME + " where " + TimelineTables.Questions.COL_CATEGORY + "='" + category + "'" + " AND " +
        TimelineTables.Questions.COL_QUESTION + "='" + question + "'" + " AND " + TimelineTables.Questions.COL_YEAR + "='" + year + "'" );
    }

    /*
    Returns zero if table is empty
     */

    public int getLowestScore(SQLiteDatabase db){
        Cursor c = db.query(TimelineTables.HighScore.TABLE_NAME, new String[]{"min(" + TimelineTables.HighScore.COL_SCORE + ")"}, null, null,
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
    Returns a cursor with the name of all categories in the question table
     */
    public Cursor getAllCategories (SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TimelineTables.Questions.COL_CATEGORY};
        cursor = db.query(true,TimelineTables.Questions.TABLE_NAME, projections, null,null, null,null ,null,null);
        return cursor;
    }

    /*
    Returns a cursor with all categories to which user questions has been added
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Cursor getCustomCategories (SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TimelineTables.Questions.COL_CATEGORY, TimelineTables.Questions.COL_BOOLEAN};
        cursor = db.query(true,TimelineTables.Questions.TABLE_NAME, projections, TimelineTables.Questions.COL_BOOLEAN + "=?", new String [] {"0"},null, null,null ,null,null);
        return cursor;
    }
    /*
    Returns a cursor with all user added questions in a specified category
     */

    public Cursor getAddedQuestions (String category, SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TimelineTables.Questions.COL_CATEGORY, TimelineTables.Questions.COL_QUESTION, TimelineTables.Questions.COL_YEAR, TimelineTables.Questions.COL_BOOLEAN};
        String selection = TimelineTables.Questions.COL_BOOLEAN + " = '0'"
                +" AND " + TimelineTables.Questions.COL_CATEGORY + " = '"+category+"'";


        cursor = db.query(TimelineTables.Questions.TABLE_NAME, projections, selection,null, null,null,null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no updates for the moment
    }
}
