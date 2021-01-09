package com.decockwgu196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wgu196final.model.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Query("SELECT * FROM course_table ORDER BY id")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE course_table.term_id == :id")
    LiveData<List<Course>> getCoursesByTerm(int id);

    @Delete()
    void delete(Course course);

    @Query("SELECT * FROM course_table WHERE course_table.id == :id")
    LiveData<Course> get(int id);
}
