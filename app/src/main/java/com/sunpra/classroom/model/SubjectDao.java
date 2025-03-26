package com.sunpra.classroom.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Query("DELETE FROM Subject WHERE user_id = :id")
    void deleteSubjectsHavingIds(int id);
}
