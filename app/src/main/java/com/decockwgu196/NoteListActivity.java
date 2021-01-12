package com.decockwgu196;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.NoteAdapter;
import com.decockwgu196.model.CourseViewModel;
import com.decockwgu196.model.Note;
import com.decockwgu196.model.NoteViewModel;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener{
    CourseViewModel courseViewModel;
    NoteViewModel noteViewModel;

    private NoteAdapter noteAdapter;

    TextView title;
    TextView text;
    RecyclerView recyclerView;


    ArrayList<Note> filteredNotes = new ArrayList<>();

    int id; //course id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        title = findViewById(R.id.note_view_title);
        text = findViewById(R.id.note_view_text);
        recyclerView = findViewById(R.id.note_list);

        Bundle data = getIntent().getExtras();
        if(data != null){
            id = data.getInt("course_id");
        }

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(NoteListActivity.this
                .getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, notes -> {
            for(Note note : notes){
                if (note.getCourseId() == id) {
                    filteredNotes.add(note);
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            noteAdapter = new NoteAdapter(filteredNotes, NoteListActivity.this, this);
            recyclerView.setAdapter(noteAdapter);
        });
    }

    @Override
    public void onNoteClick(int position) {

    }
}