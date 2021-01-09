package com.decockwgu196.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.decockwgu196.database.TermRepository;
import com.example.wgu196final.model.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    public static TermRepository repository;
    public final LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllData();
    }

    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }

    public static void insert(Term term){
        repository.insert(term);
    }

    public LiveData<Term> get(int id){
        return repository.get(id);
    }

    public static void update(Term term){
        repository.update(term);
    }

    public static void delete(Term term){
        repository.delete(term);
    }

}
