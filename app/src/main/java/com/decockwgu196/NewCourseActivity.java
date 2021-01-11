package com.decockwgu196;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

import com.decockwgu196.model.Course;
import com.decockwgu196.model.CourseViewModel;
import com.decockwgu196.model.TermViewModel;

import java.util.Calendar;

import static com.decockwgu196.UpdateTermActivity.FLAG;

public class NewCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TERM_ID = "term_id";

    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private EditText instructorName;
    private EditText instructorPhone;
    private EditText instructorEmail;

    private Button addCourseBtn;
    private Spinner spinner;
    private String status;

    private int date;
    private int month;
    private int year;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        title = findViewById(R.id.courseTitle);
        startDate = findViewById(R.id.courseStartDate);
        endDate = findViewById(R.id.courseEndDate);
        instructorName = findViewById(R.id.instructorName);
        instructorPhone = findViewById(R.id.instructorPhone);
        instructorEmail = findViewById(R.id.instructorEmail);
        addCourseBtn = findViewById(R.id.addCourseBtn);


        spinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(NewCourseActivity.this
                .getApplication())
                .create(TermViewModel.class);

        Bundle data = getIntent().getExtras();
        String previousActivity = getIntent().getExtras().getString(FLAG);

        if(data != null){
            id = data.getInt(TermViewActivity.TERM_ID);
        }

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(NewCourseActivity.this.getApplication())
                .create(CourseViewModel.class);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                date = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewCourseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        startDate.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                date = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewCourseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        endDate.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        addCourseBtn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(startDate.getText()) || TextUtils.isEmpty(endDate.getText()) || TextUtils.isEmpty(instructorName.getText()) || TextUtils.isEmpty(instructorPhone.getText()) || TextUtils.isEmpty(instructorEmail.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Course course = new Course(title.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), status, instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), id);
                CourseViewModel.insert(course);
                Intent intent = new Intent(this, TermViewActivity.class);
                intent.putExtra(TERM_ID, id);
                intent.putExtra(FLAG, "course");
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}