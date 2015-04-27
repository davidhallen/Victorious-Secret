package bodega.timeline_alfa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by victornyden on 15-04-27.
 */
public class dbManager extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "questions";

    public static final String COL_ID = BaseColumns._ID;

    public static final String COL_QUESTION = "question";

    public static final String COL_YEAR = "Year";

    private static final String DATABASE_NAME = "question.db";

    private static final int DATABASE_VERSION = 1;

    public dbManager (Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("

                + COL_QUESTION + " TEXT NOT NULL,"

                + COL_YEAR + " INTEGER"

                + ");");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");

        onCreate(db);

    }

    public long insert(String tableName, ContentValues values) {

        return getWritableDatabase().insert(tableName, null, values);

    }

    public int update(String tableName, long id, ContentValues values) {

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().update(tableName, values, selection, selectionArgs);

    }

    public int delete(String tableName, long id) {

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().delete(tableName, selection, selectionArgs);

    }

}


