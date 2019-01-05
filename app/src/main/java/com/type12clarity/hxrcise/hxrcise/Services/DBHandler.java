package com.type12clarity.hxrcise.hxrcise.Services;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import com.type12clarity.hxrcise.hxrcise.Entities.WorkOut;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "words.db";
    private static final String TABLE_WORKOUT = "workoutevents";
    private static final String COLUMN_WOID = "woid";
    private static final String COLUMN_DATETIME = "datetime";
    private static final String COLUMN_LENGTH = "length";

    private static final String TABLE_SEARCHWORDS = "searchwords";
    private static final String COLUMN_WID = "id";
    private static final String COLUMN_SEARCHWORD = "searchword";
    private static DBHandler sInstance;

    public static synchronized  DBHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext(),DATABASE_NAME,null,DATABASE_VERSION);
        }
        return sInstance;
    }

    private DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        System.out.println("mdbh constructor called");
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUT +"(" +
                COLUMN_WOID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LENGTH + " TEXT, " + COLUMN_DATETIME + " VARCHAR(12)" +
                " )";
        db.execSQL(query);
        System.out.println("mdbh on create called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_WORKOUT);
        onCreate(db);
        System.out.println("mdbh on upgrade called");
    }

    public void addEntry(WorkOut workOut) {
        long val;
        ContentValues values = new ContentValues();
        values.put(COLUMN_WOID, workOut.get_word());
        values.put(COLUMN_DATETIME, workOut.get_datetime());
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            val = db.insertOrThrow(TABLE_WORKOUT, null, values);
            db.setTransactionSuccessful();
            System.out.println("set trans success");
            System.out.println("rows inserted "+Long.valueOf(val).toString());
        }
        catch (android.database.sqlite.SQLiteConstraintException e) {
            System.out.println("SQLiteConstraintException:" + e.getMessage());
        }
        catch (android.database.sqlite.SQLiteException e) {
            System.out.println("SQLiteException:" + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
        finally {
            db.endTransaction();
            System.out.println("end trans");
        }
    }


    public String getEntryCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_WORKOUT;
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        String count= Integer.valueOf(c.getCount()).toString();
        db.close();
        c.close();
        return count;
    }

    public ArrayList<String> getWorkouts() {
        ArrayList<String> searchlist = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_SEARCHWORDS;//retrieve data from the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                searchlist.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return searchlist;
    }

    public void deleteDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
        return;
    }
}