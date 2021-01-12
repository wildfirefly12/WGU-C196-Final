package com.decockwgu196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.decockwgu196.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * FROM note_table ORDER BY id")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE note_table.id == :id")
    LiveData<Note> get(int id);

    @Delete
    void delete(Note note);
}
