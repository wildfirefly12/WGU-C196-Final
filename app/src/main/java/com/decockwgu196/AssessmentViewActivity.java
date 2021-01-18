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

import com.decockwgu196.model.Assessment;
import com.decockwgu196.model.AssessmentViewModel;

public class AssessmentViewActivity extends AppCompatActivity {

    AssessmentViewModel assessmentViewModel;

    TextView title;
    TextView start;
    TextView end;
    TextView type;
    ImageButton edit;
    ImageButton delete;

    private int id;
    private Assessment assessment;
    private int courseId;

    private Observer<Assessment> assessmentObserver;
    private LiveData<Assessment> assessmentLiveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);

        title = findViewById(R.id.assessment_view_title);
        start = findViewById(R.id.assessment_view_start);
        end = findViewById(R.id.assessment_view_end);
        type = findViewById(R.id.assessment_view_type);
        edit = findViewById(R.id.assessment_view_edit);
        delete = findViewById(R.id.assessment_view_delete);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Assessment");
        actionBar.show();

        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(AssessmentViewActivity.this
                .getApplication())
                .create(AssessmentViewModel.class);

        Bundle data = getIntent().getExtras();

        if(data != null){
            id = getIntent().getExtras().getInt("assessment_id");
            assessmentObserver = assessment -> {
                title.setText(assessment.getTitle());
                start.setText(assessment.getStartDate());
                end.setText(assessment.getEndDate());
                type.setText(assessment.getType());
                courseId = assessment.getCourseId();
                this.assessment = assessment;
            };
            assessmentLiveData = assessmentViewModel.get(id);
            assessmentLiveData.observe(this, assessmentObserver);
        }
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

    public void editAssessment(View view){
        Intent intent = new Intent(this, UpdateAssessmentActivity.class);
        intent.putExtra("assessment_id", id);
        startActivity(intent);
    }

    public void deleteAssessment(View view){
        assessmentLiveData.removeObservers(this);
        assessmentViewModel.delete(assessment);
        Intent intent = new Intent(this, CourseViewActivity.class);
        intent.putExtra("course_id", courseId);
        startActivity(intent);
    }
}