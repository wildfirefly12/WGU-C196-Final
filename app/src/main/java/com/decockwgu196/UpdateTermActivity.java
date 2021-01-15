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

public class UpdateTermActivity extends AppCompatActivity {
    TermViewModel termViewModel;

    EditText title;
    EditText start;
    EditText end;
    Button submit;

    private int date;
    private int month;
    private int year;

    private int id;//term courseId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_term);

        title = findViewById(R.id.update_term_title);
        start = findViewById(R.id.update_term_start);
        end = findViewById(R.id.update_term_end);
        submit = findViewById(R.id.update_term_button);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Term");
        actionBar.show();

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(UpdateTermActivity.this
                .getApplication())
                .create(TermViewModel.class);

        Bundle data = getIntent().getExtras();
        if(data != null){
            id = data.getInt(TermViewActivity.TERM_ID);
            termViewModel.get(id).observe(this, term -> {
                title.setText(term.getTitle());
                start.setText(term.getStartDate());
                end.setText(term.getEndDate());
            });

        }

        start.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            date = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTermActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    start.setText(month + "/" + dayOfMonth + "/" + year);
                }
            }, year, month, date);
            datePickerDialog.show();
        });

        end.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            date = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTermActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    end.setText(month + "/" + dayOfMonth + "/" + year);
                }
            }, year, month, date);
            datePickerDialog.show();
        });

        submit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(start.getText()) || TextUtils.isEmpty(end.getText())){
                Context context = getApplicationContext();
                Toast.makeText(context, "Please fill out missing info.", Toast.LENGTH_SHORT).show();
            } else {
                Term term = new Term(title.getText().toString(), start.getText().toString(), end.getText().toString());
                term.setId(id);
                TermViewModel.update(term);
                Intent intent = new Intent(this, TermViewActivity.class);
                intent.putExtra("term_id", term.getId());
                intent.putExtra("flag", "update");
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