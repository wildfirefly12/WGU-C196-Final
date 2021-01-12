package com.decockwgu196.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.decockwgu196.database.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    public static NoteRepository repository;
    public final LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public static void insert(Note note){
        repository.insert(note);
    }

    public static void update(Note note){
        repository.update(note);
    }

    public LiveData<Note> get(int id){
        return repository.get(id);
    }

    public static void delete(Note note){
        repository.delete(note);
    }
}
