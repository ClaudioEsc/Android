package com.claudio.passwords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class PasswordDao {
    private DBHelper dbHelper;
    private String[] allColumns = {
            Password.COLUMN_ID,
            Password.COLUMN_DESCRIPTION,
            Password.COLUMN_USERNAME,
            Password.COLUMN_PASSWORD};

    public PasswordDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Password password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Password.COLUMN_DESCRIPTION, password.getDescription());
        values.put(Password.COLUMN_USERNAME, password.getUsername());
        values.put(Password.COLUMN_PASSWORD, password.getPassword());

        long id = db.insert(Password.TABLE, null, values);
        db.close();
        return (int) id;
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                Password.TABLE,
                Password.COLUMN_ID + "= ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public void update(Password password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Password.COLUMN_DESCRIPTION, password.getDescription());
        values.put(Password.COLUMN_USERNAME, password.getUsername());
        values.put(Password.COLUMN_PASSWORD, password.getPassword());

        db.update(
                Password.TABLE,
                values,
                Password.COLUMN_ID + " = ?",
                new String[] { String.valueOf(password.getId()) });
        db.close();
    }

    public List<Password> getAllPasswords() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Password> passwords = new ArrayList<Password>();

        Cursor cursor = db.query(
                Password.TABLE,
                allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Password password = cursorToPassword(cursor);
            passwords.add(password);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return passwords;
    }

    private Password cursorToPassword(Cursor cursor) {
        return new Password(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
    }

    public Password getPasswordById(int id){
        Password password = new Password();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                Password.TABLE,
                allColumns,
                Password.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            password = cursorToPassword(cursor);
        }

        cursor.close();
        db.close();

        return password;
    }
}
