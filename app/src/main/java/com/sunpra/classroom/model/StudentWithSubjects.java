package com.sunpra.classroom.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class StudentWithSubjects implements Serializable {
    @Embedded
    public Student student;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id"
    )
    public List<Subject> subjects;
}
