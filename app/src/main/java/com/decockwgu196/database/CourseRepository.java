package com.decockwgu196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.decockwgu196.model.Course;

import java.util.List;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    public CourseRepository(Application application) {
        Database db = Database.getDatabase(application);
        courseDao = db.courseDao();

        allCourses = courseDao.getAllCourses();
        System.out.println(allCourses);
    }

    public LiveData<List<Course>> getAllData(){
        return allCourses;
    }

    public LiveData<List<Course>> getCoursesByTerm(int id){
        return courseDao.getCoursesByTerm(id);
    }

    public void insert(Course course){
        Database.databaseWriteExecutor.execute(() -> {
            courseDao.insert(course);
        });
    }

    public LiveData<Course> get(int id){
        return courseDao.get(id);
    }

    public void update(Course course){
        Database.databaseWriteExecutor.execute(() -> {
            courseDao.update(course);
        });
    }

    public void delete(Course course){
        Database.databaseWriteExecutor.execute(() -> {
            courseDao.delete(course);
        });
    }
}
