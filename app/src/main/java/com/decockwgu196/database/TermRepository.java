package com.decockwgu196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.decockwgu196.model.Term;

import java.util.List;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;

    public TermRepository(Application application) {
        Database db = Database.getDatabase(application);
        termDao = db.termDao();

        allTerms = termDao.getAllTerms();
    }

    public LiveData<List<Term>> getAllData(){
        return allTerms;
    }

    public void insert(Term term){
        Database.databaseWriteExecutor.execute(() -> {
            termDao.insert(term);
        });
    }

    public LiveData<Term> get(int id){
        return termDao.get(id);
    }

    public void update(Term term){
        Database.databaseWriteExecutor.execute(() -> termDao.update(term));
    }

    public void delete(Term term){
        Database.databaseWriteExecutor.execute(() -> termDao.delete(term));
    }
}
