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

import com.decockwgu196.model.Assessment;
import com.decockwgu196.model.AssessmentViewModel;

import java.util.Calendar;

public class NewAssessmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AssessmentViewModel assessmentViewModel;

    private EditText title;
    private EditText start;
    private EditText end;
    private String type;
    private Spinner spinner;
    private Button submit;

    private int date;
    private int month;
    private int year;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        title = findViewById(R.id.new_assessment_title);
        start = findViewById(R.id.new_assessment_start);
        end = findViewById(R.id.new_assessment_end);
        submit = findViewById(R.id.new_assessment_button);

        spinner = findViewById(R.id.new_assessment_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Bundle data = getIntent().getExtras();

        if(data != null){
            id = data.getInt("course_id");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                date = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAssessmentActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAssessmentActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
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
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(start.getText()) || TextUtils.isEmpty(end.getText()) || TextUtils.isEmpty(type)){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Assessment assessment = new Assessment(title.getText().toString(), start.getText().toString(), end.getText().toString(), type, id);
                AssessmentViewModel.insert(assessment);
                Intent intent = new Intent(this, CourseViewActivity.class);
                intent.putExtra("course_id", id);
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
        type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}