package com.decockwgu196;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.decockwgu196.model.Note;
import com.decockwgu196.model.NoteViewModel;

public class NoteViewActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;

    TextView title;
    TextView text;
    ImageButton share;

    private Note note;
    private int id;
    private int courseId;
    private Observer<Note> noteObserver;
    private LiveData<Note> noteLiveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        title = findViewById(R.id.note_view_title);
        text = findViewById(R.id.note_view_text);
        share = findViewById(R.id.note_view_share);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Note");
        actionBar.show();

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(NoteViewActivity.this
                .getApplication())
                .create(NoteViewModel.class);

        Bundle data = getIntent().getExtras();

        if(data != null){
            id = getIntent().getExtras().getInt("note_id");
            noteObserver = note -> {
                title.setText(note.getTitle());
                text.setText(note.getText());
                courseId = note.getCourseId();
                this.note = note;
            };
            noteLiveData = noteViewModel.get(id);
            noteLiveData.observe(this, noteObserver);
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, note.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT, note.getText());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, CourseViewActivity.class);
                intent.putExtra("course_id", courseId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editNote(View view){
        Intent intent = new Intent(this, UpdateNoteActivity.class);
        intent.putExtra("note_id", id);
        startActivity(intent);
    }

    public void deleteNote(View view){
        noteLiveData.removeObservers(this);
        noteViewModel.delete(note);
        Intent intent = new Intent(this, NoteListActivity.class);
        intent.putExtra("course_id", courseId);
        startActivity(intent);
    }

}