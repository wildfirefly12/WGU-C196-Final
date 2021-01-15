package com.decockwgu196;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
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

        title = findViewById(R.id.new_term_title);
        startDate = findViewById(R.id.new_term_start);
        endDate = findViewById(R.id.new_term_end);
        addTermBtn = findViewById(R.id.new_term_submit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Term");
        actionBar.show();

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
                Intent intent = new Intent(this, TermListActivity.class);
                startActivity(intent);
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
}