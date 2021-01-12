package com.decockwgu196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.decockwgu196.model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        Database db = Database.getDatabase(application);
        noteDao = db.noteDao();

        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void insert(Note note){
        Database.databaseWriteExecutor.execute(() -> {
            noteDao.insert(note);
        });
    }

    public void update(Note note){
        Database.databaseWriteExecutor.execute(() -> {
            noteDao.update(note);
        });
    }

    public LiveData<Note> get(int id){
        return noteDao.get(id);
    }

    public void delete(Note note){
        Database.databaseWriteExecutor.execute(() -> {
            noteDao.delete(note);
        });
    }
}
