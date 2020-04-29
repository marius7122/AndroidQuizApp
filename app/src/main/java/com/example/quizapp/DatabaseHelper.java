package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String table_name = "score_table";
    private static final String col1 = "ID";
    private static final String col2 = "Category";
    private static final String col3 = "Difficulty";
    private static final String col4 = "Score";


    public DatabaseHelper(Context context) {
        super(context, table_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col2 +" TEXT, " + col3 + " TEXT, " + col4 + " TEXT);";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + table_name);
        onCreate(db);
    }

    public boolean addData(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, score.category);
        contentValues.put(col3, score.difficulty);
        contentValues.put(col4, score.score);

        long result = db.insert(table_name, null, contentValues);

        //if date is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table_name;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }
}
