package com.sunpra.classroom.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sunpra.classroom.model.User;

public class SQLAppDatabase extends SQLiteOpenHelper {

    public static final int VERSION = 2;
    public static final String DATABASE_NAME = "sql_app_database";

    public SQLAppDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // WRITE QUERY TO CREATE TABLE
        db.execSQL("CREATE TABLE user (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER NOT NULL, gender TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ADDING new column, gender to user table in version 2.
        if(newVersion == 2) {
            db.execSQL("ALTER TABLE user ADD COLUMN gender TEXT NOT NULL default 'UNKNOWN'");
        }
    }

    //region Data Manipulation
    public long addUser(User user) {
        SQLiteDatabase theDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("age", user.getAge());

        return theDatabase.insert("user", null, contentValues);
    }

    public void deleteUser(Long id) {
        SQLiteDatabase theDatabase = getWritableDatabase();

        theDatabase.delete(
                "user",
                "id = ?",
                new String[]{id.toString()}
        );
    }

    public void updateUserAge(Long id, int age){
        SQLiteDatabase theDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("age", age);

        theDatabase.update(
                "user",
                contentValues,
                "id = ?",
                new String[]{id.toString()}
        );
    }

    public Cursor getUsers(){
        SQLiteDatabase theDatabase = getReadableDatabase();
        return theDatabase.rawQuery("SELECT * FROM user", null);
    }

    //endregion
}
