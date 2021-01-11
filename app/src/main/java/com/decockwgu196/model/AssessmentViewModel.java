package com.decockwgu196.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.decockwgu196.database.AssessmentRepository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    public static AssessmentRepository repository;
    public final LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        repository = new AssessmentRepository(application);
        allAssessments = repository.getAllData();
    }

    public LiveData<List<Assessment>> getAllAssessments(){
        return allAssessments;
    }

/*    public LiveData<List<Assessment>> getAsse(int courseId){
        return repository.getCoursesByTerm(courseId);
    }*/

    public static void insert(Assessment assessment){
        repository.insert(assessment);
    }

    public LiveData<Assessment> get(int id){
        return repository.get(id);
    }

    public static void update(Assessment assessment){
        repository.update(assessment);
    }

    public static void delete(Assessment assessment){
        repository.delete(assessment);
    }
}
