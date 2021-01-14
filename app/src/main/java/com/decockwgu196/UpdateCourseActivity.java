package com.decockwgu196;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.decockwgu196.model.Course;
import com.decockwgu196.model.CourseViewModel;

import java.util.Calendar;

public class UpdateCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CourseViewModel courseViewModel;

    private EditText title;
    private EditText start;
    private EditText end;
    private EditText instructorName;
    private EditText instructorPhone;
    private EditText instructorEmail;

    private Button submit;
    private Spinner spinner;
    private String status;

    private int date;
    private int month;
    private int year;

    private int courseId;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        title = findViewById(R.id.update_course_title);
        start = findViewById(R.id.update_course_start);
        end = findViewById(R.id.update_course_end);
        instructorName = findViewById(R.id.update_course_name);
        instructorPhone = findViewById(R.id.update_course_phone);
        instructorEmail = findViewById(R.id.update_course_email);
        submit = findViewById(R.id.update_course_submit);


        spinner = findViewById(R.id.update_course_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(UpdateCourseActivity.this.getApplication())
                .create(CourseViewModel.class);

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(UpdateCourseActivity.this
                .getApplication())
                .create(CourseViewModel.class);

        Bundle data = getIntent().getExtras();
        if(data != null){
            courseId = data.getInt("course_id");
            courseViewModel.get(courseId).observe(this, course -> {
                title.setText(course.getTitle());
                start.setText(course.getStartDate());
                end.setText(course.getEndDate());
                instructorName.setText(course.getInstructor());
                instructorPhone.setText(course.getPhone());
                instructorEmail.setText(course.getEmail());
                termId = course.getTermId();
            });

        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                date = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateCourseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        start.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                date = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateCourseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        end.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(start.getText()) || TextUtils.isEmpty(end.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Course course = new Course(title.getText().toString(), start.getText().toString(), end.getText().toString(), status, instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), termId);
                course.setCourseId(courseId);
                CourseViewModel.update(course);
                Intent intent = new Intent(this, TermViewActivity.class);
                intent.putExtra("course_id", course.getCourseId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
