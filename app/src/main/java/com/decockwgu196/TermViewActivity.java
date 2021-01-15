package com.decockwgu196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.CourseAdapter;
import com.decockwgu196.model.Course;
import com.decockwgu196.model.CourseViewModel;
import com.decockwgu196.model.TermViewModel;

import java.util.ArrayList;

public class TermViewActivity extends AppCompatActivity implements CourseAdapter.OnCourseClickListener {
    public static final String TERM_ID = "term_id";

    TextView title;
    TextView startDate;
    TextView endDate;
    Button addCourseBtn;
    RecyclerView recyclerView;

    private ArrayList<Course> filteredCourses = new ArrayList<>();

    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    private CourseAdapter courseAdapter;

    int termId; //term courseId from TermListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        title = findViewById(R.id.term_view_title);
        startDate = findViewById(R.id.term_view_start);
        endDate = findViewById(R.id.term_view_end);

        recyclerView = findViewById(R.id.term_view_course_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Term");
        actionBar.show();

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermViewActivity.this
                .getApplication())
                .create(TermViewModel.class);

        Bundle data = getIntent().getExtras();

        if(data != null){
            termId = data.getInt("term_id");
            termViewModel.get(termId).observe(this, term -> {
                title.setText(term.getTitle());
                startDate.setText(term.getStartDate());
                endDate.setText(term.getEndDate());
            });
        }

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(TermViewActivity.this
                .getApplication())
                .create(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, courses -> {
            for(Course course : courses){
                if(course.getTermId() == termId){
                    filteredCourses.add(course);
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            courseAdapter = new CourseAdapter(filteredCourses, TermViewActivity.this, this);
            recyclerView.setAdapter(courseAdapter);
        });
    }

    public void addCourseButton(View view){
        Intent intent = new Intent(this, NewCourseActivity.class);
        intent.putExtra(TERM_ID, termId);
        startActivity(intent);
    }

    public void editTerm(View view){
        Intent intent = new Intent(this, UpdateTermActivity.class);
        intent.putExtra(TERM_ID, termId);
        startActivity(intent);
    }

    public void deleteTerm(View view){
        if (filteredCourses.size() == 0) {
            termViewModel.get(termId).observe(this, term -> {
                TermViewModel.delete(term);
            });
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            Toast.makeText(context, "Please delete all assessments first.", Toast.LENGTH_SHORT).show();
        }
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
    public void onCourseClick(int position) {
        Course course = filteredCourses.get(position);

        Intent intent = new Intent(this, CourseViewActivity.class);
        intent.putExtra("course_id", course.getCourseId());
        intent.putExtra("flag", "courses");
        startActivity(intent);

    }
}