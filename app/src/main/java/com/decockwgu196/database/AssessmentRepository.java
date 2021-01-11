package com.decockwgu196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.decockwgu196.model.Assessment;

import java.util.List;

public class AssessmentRepository {
    private AssessmentDao assessmentDao;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentRepository(Application application){
        Database db = Database.getDatabase(application);
        assessmentDao = db.assessmentDao();

        allAssessments = assessmentDao.getAllAssessments();
    }

    public LiveData<List<Assessment>> getAllData(){
        return allAssessments;
    }

//    public LiveData<List<Course>> getCoursesByTerm(int courseId){
//        return assessmentDao.getAssessmentsByCourses(courseId);
//    }

    public void insert(Assessment assessment){
        Database.databaseWriteExecutor.execute(() -> {
            assessmentDao.insert(assessment);
        });
    }

    public LiveData<Assessment> get(int id){
        return assessmentDao.get(id);
    }

    public void update(Assessment assessment){
        Database.databaseWriteExecutor.execute(() -> {
            assessmentDao.update(assessment);
        });
    }

    public void delete(Assessment assessment){
        Database.databaseWriteExecutor.execute(() -> {
            assessmentDao.delete(assessment);
        });
    }
}
