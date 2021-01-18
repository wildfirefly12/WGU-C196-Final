package com.decockwgu196;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.NoteAdapter;
import com.decockwgu196.model.CourseViewModel;
import com.decockwgu196.model.Note;
import com.decockwgu196.model.NoteViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener{
    CourseViewModel courseViewModel;
    NoteViewModel noteViewModel;

    private NoteAdapter noteAdapter;

    TextView title;
    RecyclerView recyclerView;

    ArrayList<Note> filteredNotes = new ArrayList<>();

    private int courseId; //course courseId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        title = findViewById(R.id.note_view_title);
        recyclerView = findViewById(R.id.note_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notes");
        actionBar.show();

        Bundle data = getIntent().getExtras();
        if(data != null){
            courseId = data.getInt("course_id");
        }

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(NoteListActivity.this
                .getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, notes -> {
            for(Note note : notes){
                if (note.getCourseId() == courseId) {
                    filteredNotes.add(note);
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            noteAdapter = new NoteAdapter(filteredNotes, NoteListActivity.this, this);
            recyclerView.setAdapter(noteAdapter);
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(int position) {
        Note note = Objects.requireNonNull(noteViewModel.allNotes.getValue()).get(position);
        Intent intent = new Intent(this, NoteViewActivity.class);
        intent.putExtra("note_id", note.getId());
        startActivity(intent);
    }

    public void newNote(View view){
        Intent intent = new Intent(this, NewNoteActivity.class);
        intent.putExtra("course_id", courseId);
        startActivity(intent);
    }


}