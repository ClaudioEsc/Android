package com.claudio.passwords;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "password.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Password.TABLE  + "("
                + Password.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Password.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + Password.COLUMN_USERNAME + " TEXT NOT NULL, "
                + Password.COLUMN_PASSWORD + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Password.TABLE);
        onCreate(db);
    }
}
