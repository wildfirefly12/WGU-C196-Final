package com.decockwgu196.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wgu196final.model.Assessment;
import com.example.wgu196final.model.Term;

import java.util.List;

@Dao
public interface AssessmentDao {
    //CRUD operations
    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Query("SELECT * FROM assessment_table WHERE course_id = :term")
    List<Assessment> getAssessmentsByTerm(Term term);

    @Delete
    void delete();

}
