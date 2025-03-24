package com.sunpra.classroom.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Student.class,
                        childColumns = "user_id",
                        parentColumns = "id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Subject implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "subject_name")
    private String subjectName;
    @ColumnInfo(name = "user_id")
    private int userId;

    public Subject(int id, String subjectName, int userId) {
        this.id = id;
        this.subjectName = subjectName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
