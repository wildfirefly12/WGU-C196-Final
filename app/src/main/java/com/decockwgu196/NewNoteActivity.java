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

public class NewNoteActivity extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private NoteViewModel noteViewModel;

    private EditText title;
    private EditText text;
    private Button submit;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = findViewById(R.id.new_note_title);
        text = findViewById(R.id.new_note_text);
        submit = findViewById(R.id.new_note_submit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle data = getIntent().getExtras();

        if(data != null){
            id = data.getInt("course_id");
        }

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(NewNoteActivity.this.getApplication())
                .create(CourseViewModel.class);

        submit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(text.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Note note = new Note(title.getText().toString(), text.getText().toString(), id);
                NoteViewModel.insert(note);
                Intent intent = new Intent(this, TermViewActivity.class);
                intent.putExtra("course_id", id);
                startActivity(intent);
                finish();
            }
        });

    }
}