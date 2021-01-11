package com.decockwgu196;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.decockwgu196.model.Term;
import com.decockwgu196.model.TermViewModel;

import java.util.Calendar;

public class NewTermActivity extends AppCompatActivity {
    private TermViewModel termViewModel;

    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private Button addTermBtn;

    private int date;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        title = findViewById(R.id.termTitle);
        startDate = findViewById(R.id.termStartDate);
        endDate = findViewById(R.id.termEndDate);
        addTermBtn = findViewById(R.id.addTermBtn);

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(NewTermActivity.this.getApplication())
                .create(TermViewModel.class);

        startDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            date = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewTermActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    startDate.setText(month + "/" + dayOfMonth + "/" + year);
                }
            }, year, month, date);
            datePickerDialog.show();
        });

        endDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            date = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(NewTermActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    endDate.setText(month + "/" + dayOfMonth + "/" + year);
                }
            }, year, month, date);
            datePickerDialog.show();
        });

        addTermBtn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(startDate.getText()) || TextUtils.isEmpty(endDate.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Term term = new Term(title.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
                TermViewModel.insert(term);
                Intent intent = new Intent(this, TermsActivity.class);
                startActivity(intent);
            }
        });
    }
}