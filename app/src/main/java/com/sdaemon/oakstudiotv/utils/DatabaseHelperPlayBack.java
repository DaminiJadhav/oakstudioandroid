package com.sdaemon.oakstudiotv.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sdaemon.oakstudiotv.dto.PlaybackDTO;

import java.util.ArrayList;

/**
 * Created by Sayem on 12/5/2017.
 */

public class DatabaseHelperPlayBack extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "playback_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "playback_table";
    private static final String KEY_ID = "id";
    private static final String KEY_URL_ID = "urlId";
    private static final String KEY_PLAY_BACK_POSITION = "playBackPosition";
  //  private static final String KEY_EMAIL = "email";
  //  private static final String KEY_PHONE = "phone";

    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_URL_ID + " TEXT NOT NULL, "+
            KEY_PLAY_BACK_POSITION + " TEXT NOT NULL " +
            "); ";

    public DatabaseHelperPlayBack(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_TEACHERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEACHERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        onCreate(db);
    }

    public long addTeachersDetail(String urlId, String playBackPosition) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_URL_ID, urlId);
        values.put(KEY_PLAY_BACK_POSITION, playBackPosition);
        //insert row in table
        long insert = db.insert(TABLE_USER, null, values);

        return insert;
    }

    public ArrayList<PlaybackDTO> getAllTeachers() {
        ArrayList<PlaybackDTO> teachersModelArrayList = new ArrayList<PlaybackDTO>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                PlaybackDTO teachersModel = new PlaybackDTO();
                teachersModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                teachersModel.setUrlId(c.getString(c.getColumnIndex(KEY_URL_ID)));
                teachersModel.setPlayBackPosition(c.getString(c.getColumnIndex(KEY_PLAY_BACK_POSITION)));
                // adding to list
                teachersModelArrayList.add(teachersModel);
            } while (c.moveToNext());
        }
        return teachersModelArrayList;
    }

//    public int updateTeachers(int id, String urlId, String playBackPosition) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Creating content values
//        ContentValues values = new ContentValues();
//        values.put(KEY_URL_ID, urlId);
//        values.put(KEY_PLAY_BACK_POSITION, playBackPosition);
//        // update row in table base on students.is value
//        return db.update(TABLE_USER, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(id)});
//    }

    public int updateTeachers(String urlId, String playBackPosition) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_URL_ID, urlId);
        values.put(KEY_PLAY_BACK_POSITION, playBackPosition);
        // update row in table base on students.is value
        return db.update(TABLE_USER, values, KEY_URL_ID + " = ?",
                new String[]{String.valueOf(urlId)});
    }




    public void deleteUSer(int id) {

        // delete row in table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}
