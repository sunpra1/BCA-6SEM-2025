package com.sunpra.classroom.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    List<StudentWithSubjects> getAll();

    @Insert
    long insertStudent(Student student);

}
