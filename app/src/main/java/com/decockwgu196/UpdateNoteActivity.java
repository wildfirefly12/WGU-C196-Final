package com.decockwgu196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.decockwgu196.model.CourseViewModel;
import com.decockwgu196.model.Note;
import com.decockwgu196.model.NoteViewModel;

public class UpdateNoteActivity extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private NoteViewModel noteViewModel;

    private EditText title;
    private EditText text;
    private Button submit;

    private int noteId;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        title = findViewById(R.id.update_note_title);
        text = findViewById(R.id.update_note_text);
        submit = findViewById(R.id.update_note_submit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(UpdateNoteActivity.this.getApplication())
                .create(NoteViewModel.class);

        Bundle data = getIntent().getExtras();

        if(data != null){
            noteId = data.getInt("note_id");
            noteViewModel.get(noteId).observe(this, note -> {
                title.setText(note.getTitle());
                text.setText(note.getText());
                courseId = note.getCourseId();
            });
        }

        submit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(text.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Note note = new Note(title.getText().toString(), text.getText().toString(), courseId);
                note.setId(noteId);
                NoteViewModel.update(note);
                Intent intent = new Intent(this, NoteViewActivity.class);
                intent.putExtra("note_id", noteId);
                startActivity(intent);
            }
        });
    }
}