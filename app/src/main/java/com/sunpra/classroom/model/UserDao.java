package com.sunpra.classroom.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.sunpra.classroom.data.SQLAppDatabase;

public class UserDao {

    private SQLAppDatabase database;

    public UserDao(SQLAppDatabase database) {
        this.database = database;
    }

    public long addUser(User user) {
        SQLiteDatabase theDatabase = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_NAME, user.getName());
        contentValues.put(User.COLUMN_AGE, user.getAge());

        return theDatabase.insert(User.TABLE_NAME, null, contentValues);
    }
}