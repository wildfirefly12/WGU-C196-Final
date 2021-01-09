package com.decockwgu196;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.CourseAdapter;
import com.decockwgu196.model.CourseViewModel;
import com.decockwgu196.model.TermViewModel;

import static com.decockwgu196.UpdateTermActivity.FLAG;

public class TermViewActivity extends AppCompatActivity implements CourseAdapter.OnCourseClickListener {
    public static final String TERM_ID = "term_id";

    TextView title;
    TextView startDate;
    TextView endDate;
    Button addCourseBtn;
    RecyclerView recyclerView;

    private CourseAdapter courseAdapter;

    int id; //term id from TermListView

    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        title = findViewById(R.id.termView_title);
        startDate = findViewById(R.id.termView_startDate);
        endDate = findViewById(R.id.termView_endDate);

        recyclerView = findViewById(R.id.termView_courseList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermViewActivity.this
                .getApplication())
                .create(TermViewModel.class);

        Bundle data = getIntent().getExtras();
        String previousActivity = getIntent().getExtras().getString(FLAG);

        if(data != null){
            if(previousActivity == "update"){
                id = data.getInt(UpdateTermActivity.TERM_ID);
                termViewModel.get(id).observe(this, term -> {
                    title.setText(term.getTitle());
                    startDate.setText(term.getStartDate());
                    endDate.setText(term.getEndDate());
                });
            } else if(previousActivity == "terms"){
                id = data.getInt(TermsActivity.TERM_ID);
                termViewModel.get(id).observe(this, term -> {
                    title.setText(term.getTitle());
                    startDate.setText(term.getStartDate());
                    endDate.setText(term.getEndDate());
                });
            } else {
                id = data.getInt(NewCourseActivity.TERM_ID);
                termViewModel.get(id).observe(this, term -> {
                    title.setText(term.getTitle());
                    startDate.setText(term.getStartDate());
                    endDate.setText(term.getEndDate());
                });
            }
        }

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(TermViewActivity.this
                .getApplication())
                .create(CourseViewModel.class);


        courseViewModel.getCoursesByTerm(id).observe(this, courses -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            courseAdapter = new CourseAdapter(courses, TermViewActivity.this, this);
            recyclerView.setAdapter(courseAdapter);
        });

    }

    public void addCourseButton(View view){
        Intent intent = new Intent(this, NewCourseActivity.class);
        intent.putExtra(TERM_ID, id);
        startActivity(intent);
    }

    public void editTermButton(View view){
        Intent intent = new Intent(this, UpdateTermActivity.class);
        intent.putExtra(TERM_ID, id);
        startActivity(intent);
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

    }
}