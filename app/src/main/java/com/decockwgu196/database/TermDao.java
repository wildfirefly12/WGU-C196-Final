package com.decockwgu196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wgu196final.model.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Query("SELECT * FROM term_table ORDER BY id")
    LiveData<List<Term>> getAllTerms();

    @Delete()
    void delete(Term term);

    @Query("SELECT * FROM term_table WHERE term_table.id == :id")
    LiveData<Term> get(int id);
}
