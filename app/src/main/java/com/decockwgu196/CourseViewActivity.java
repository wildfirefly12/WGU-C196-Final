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
import com.decockwgu196.model.CourseViewModel;

import java.util.ArrayList;

import static com.decockwgu196.UpdateTermActivity.FLAG;

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


    int id; //course id

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
        String previousActivity = getIntent().getExtras().getString(FLAG);


        if(data != null){
            id = getIntent().getExtras().getInt("courseId");
            courseViewModel.get(id).observe(this, term -> {
                title.setText(term.getTitle());
                start.setText(term.getStartDate());
                end.setText(term.getEndDate());
                status.setText(term.getStatus());
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

    public void editCourse(){

    }

    public void deleteCourse(){

    }

    public void notes(View view){
        Intent intent = new Intent(this, NoteListActivity.class);
        intent.putExtra(COURSE_ID, id);
        startActivity(intent);
    }

    @Override
    public void onAssessmentClick(int position) {

    }
}