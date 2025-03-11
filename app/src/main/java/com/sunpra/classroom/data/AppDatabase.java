package com.sunpra.classroom.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sunpra.classroom.model.Student;
import com.sunpra.classroom.model.StudentDao;
import com.sunpra.classroom.model.Subject;
import com.sunpra.classroom.model.SubjectDao;

@Database(
        entities = {
                Student.class,
                Subject.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private volatile static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            if (instance != null) return instance;
            instance = Room.databaseBuilder(
                            context,
                            AppDatabase.class,
                            "app_database"
                    )
                    .build();

            return instance;
        }
    }

    public abstract StudentDao studentDao();

    public abstract SubjectDao subjectDao();
}
