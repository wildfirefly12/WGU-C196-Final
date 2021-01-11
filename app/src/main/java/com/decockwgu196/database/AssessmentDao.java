package com.decockwgu196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.decockwgu196.model.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    //CRUD operations
    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Query("SELECT * FROM assessment_table WHERE course_id = :id")
    List<Assessment> getAssessmentsByCourses(int id);

    @Query("SELECT * FROM assessment_table ORDER BY id")
    LiveData<List<Assessment>> getAllAssessments();

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment_table WHERE assessment_table.id == :id")
    LiveData<Assessment> get(int id);
}
