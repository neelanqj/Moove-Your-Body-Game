package com.otbxsolutions.mobileapps.mooveyourbody.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the DBAdapter class for the instructions activity.
 */
public class DBAdapter {
    public static final String KEY_ROWID = "id";
    public static final String KEY_USER = "username";
    public static final String KEY_SCORE = "score";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "MooveYourBody";
    private static final String DATABASE_TABLE = "score";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "create table if not exists score (id integer primary key autoincrement, "
            + "username VARCHAR not null, score INT NOT NULL);";
    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a record into the database---
    public long insertRecord(String user, String score)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USER, user);
        initialValues.put(KEY_SCORE, score);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular record---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }


    //---retrieves all the records---
    public Cursor getAllRecords()
    {
        return db.rawQuery("SELECT * FROM "+DATABASE_TABLE+ " ORDER BY "+ KEY_SCORE+" DESC LIMIT 10",null);

                //query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_USER,KEY_SCORE}, null, null, null, KEY_SCORE + " DESC", " 10");
    }

    //---retrieves a particular record---
    public Cursor getRecord(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_USER, KEY_SCORE},
                        KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public boolean highScore(int score){
        boolean isSmaller = true;
        Cursor c = getAllRecords();

        while(c.moveToNext()){
            if(Integer.parseInt(c.getString(2)) < score) return true;
        }
        return false;
    }

    //---updates a record---
    public boolean updateRecord(long rowId, String user, String score)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USER, user);
        args.put(KEY_SCORE, score);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
