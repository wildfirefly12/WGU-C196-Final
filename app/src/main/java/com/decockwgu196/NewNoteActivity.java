package com.decockwgu196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
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

    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = findViewById(R.id.new_note_title);
        text = findViewById(R.id.new_note_text);
        submit = findViewById(R.id.new_note_submit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Note");
        actionBar.show();

        Bundle data = getIntent().getExtras();

        if(data != null){
            courseId = data.getInt("course_id");
        }

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(NewNoteActivity.this.getApplication())
                .create(CourseViewModel.class);

        submit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(text.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Note note = new Note(title.getText().toString(), text.getText().toString(), courseId);
                NoteViewModel.insert(note);
                Intent intent = new Intent(this, NoteListActivity.class);
                intent.putExtra("course_id", courseId);
                startActivity(intent);
                finish();
            }
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
}