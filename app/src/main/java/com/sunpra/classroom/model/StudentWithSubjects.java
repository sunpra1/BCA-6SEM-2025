package com.sunpra.classroom.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithSubjects {
    @Embedded
    public Student student;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id"
    )
    public List<Subject> subjects;
}
