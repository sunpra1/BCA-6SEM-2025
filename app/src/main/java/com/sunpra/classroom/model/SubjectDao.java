package com.sunpra.classroom.model;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);
}
