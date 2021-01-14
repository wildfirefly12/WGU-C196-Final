package com.decockwgu196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.AssessmentAdapter;
import com.decockwgu196.model.Assessment;
import com.decockwgu196.model.AssessmentViewModel;
import com.decockwgu196.model.Course;
import com.decockwgu196.model.CourseViewModel;

import java.util.ArrayList;


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

    private Course course;
    private int id; //course courseId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        title = findViewById(R.id.course_view_title);
        start = findViewById(R.id.course_view_start_date);
        end = findViewById(R.id.course_view_end_date);
        status = findViewById(R.id.course_view_status);

        recyclerView = findViewById(R.id.course_view_assessments);

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(CourseViewActivity.this
                .getApplication())
                .create(CourseViewModel.class);

        Bundle data = getIntent().getExtras();

        if(data != null){
            id = getIntent().getExtras().getInt("course_id");
            courseViewModel.get(id).observe(this, course -> {
                title.setText(course.getTitle());
                start.setText(course.getStartDate());
                end.setText(course.getEndDate());
                status.setText(course.getStatus());
                this.course = course;
            });
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

    public void editCourse(View view){
        Intent intent = new Intent(this, UpdateCourseActivity.class);
        intent.putExtra("course_id", id);
        intent.putExtra("term_id", course.getTermId());
        startActivity(intent);
    }

    public void deleteCourse(View view){

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

    }
}