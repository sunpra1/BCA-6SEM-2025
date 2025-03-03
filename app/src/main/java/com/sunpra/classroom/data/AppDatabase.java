package com.sunpra.classroom.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sunpra.classroom.model.Student;
import com.sunpra.classroom.model.StudentDao;

@Database(
        entities = {
                Student.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private volatile static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        synchronized (AppDatabase.class) {
            if (instance != null) return instance;
            instance = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "app_database"
            ).build();

            return instance;
        }
    }

    public abstract StudentDao studentDao();
}
