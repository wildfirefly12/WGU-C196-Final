package com.decockwgu196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.AssessmentAdapter;
import com.decockwgu196.model.Assessment;
import com.decockwgu196.model.AssessmentViewModel;
import com.decockwgu196.model.Course;
import com.decockwgu196.model.CourseViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class CourseViewActivity extends AppCompatActivity implements AssessmentAdapter.OnAssessmentClickListener {
    private static final String COURSE_ID = "course_id";
    CourseViewModel courseViewModel;
    AssessmentViewModel assessmentViewModel;

    private AssessmentAdapter assessmentAdapter;

    TextView title;
    TextView start;
    TextView end;
    TextView status;
    RecyclerView recyclerView;

    ArrayList<Assessment> filteredAssessments = new ArrayList<>();

    private int id; //course courseId
    private Course course;
    private int termId;

    private Observer<Course> courseObserver;
    private LiveData<Course> courseLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        title = findViewById(R.id.course_view_title);
        start = findViewById(R.id.course_view_start_date);
        end = findViewById(R.id.course_view_end_date);
        status = findViewById(R.id.course_view_status);

        recyclerView = findViewById(R.id.course_view_assessments);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Course");
        actionBar.show();

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(CourseViewActivity.this
                .getApplication())
                .create(CourseViewModel.class);

        Bundle data = getIntent().getExtras();

        if(data != null){
            id = getIntent().getExtras().getInt("course_id");
            courseObserver = course -> {
                title.setText(course.getTitle());
                start.setText(course.getStartDate());
                end.setText(course.getEndDate());
                status.setText(course.getStatus());
                termId = course.getTermId();
                this.course = course;
            };
            courseLiveData = courseViewModel.get(id);
            courseLiveData.observe(this, courseObserver);
        }


        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(CourseViewActivity.this
                .getApplication())
                .create(AssessmentViewModel.class);

        assessmentViewModel.getAllAssessments().observe(this, assessments -> {
            for(Assessment assessment : assessments){
                if(assessment.getCourseId() == id){
                    filteredAssessments.add(assessment);
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            assessmentAdapter = new AssessmentAdapter(filteredAssessments, CourseViewActivity.this, this);
            recyclerView.setAdapter(assessmentAdapter);
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, CourseViewActivity.class);
                intent.putExtra("term_id", termId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editCourse(View view){
        Intent intent = new Intent(this, UpdateCourseActivity.class);
        intent.putExtra("course_id", id);
        intent.putExtra("term_id", termId);
        startActivity(intent);
    }

    public void deleteCourse(View view){
        if (filteredAssessments.size() == 0) {
            courseLiveData.removeObservers(this);
            courseViewModel.delete(course);
            Intent intent = new Intent(this, TermViewActivity.class);
            intent.putExtra("term_id", termId);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            Toast.makeText(context, "Please delete all assessments first.", Toast.LENGTH_SHORT).show();
        }
    }

    public void notes(View view){
        Intent intent = new Intent(this, NoteListActivity.class);
        intent.putExtra("course_id", id);
        startActivity(intent);
    }

    public void addAssessment(View view){
        Intent intent = new Intent(this, NewAssessmentActivity.class);
        intent.putExtra("course_id", id);
        startActivity(intent);
    }

    @Override
    public void onAssessmentClick(int position) {
        Assessment assessment = Objects.requireNonNull(assessmentViewModel.allAssessments.getValue()).get(position);
        Intent intent = new Intent(this, AssessmentViewActivity.class);
        intent.putExtra("assessment_id", assessment.getId());
        startActivity(intent);
    }
}