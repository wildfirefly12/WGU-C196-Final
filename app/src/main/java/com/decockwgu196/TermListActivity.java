package com.decockwgu196;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.adapter.TermsAdapter;
import com.decockwgu196.model.TermViewModel;
import com.decockwgu196.model.Term;

import java.util.Objects;

public class TermListActivity extends AppCompatActivity implements TermsAdapter.OnTermClickListener {
    public static final String TERM_ID = "term_id";
    public static final String FLAG = "flag";
    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermsAdapter termsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.term_list_terms);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Terms");
        actionBar.show();

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermListActivity.this
                .getApplication())
                .create(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, terms -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termsAdapter = new TermsAdapter(terms, TermListActivity.this, this);
            recyclerView.setAdapter(termsAdapter);
        });

    }

    public void addTermsButton(View view){
        Intent intent = new Intent(this, NewTermActivity.class);
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
    public void onTermClick(int position) {
        Term term = Objects.requireNonNull(termViewModel.allTerms.getValue()).get(position);
        Intent intent = new Intent(this, TermViewActivity.class);
        intent.putExtra(TERM_ID, term.getId());
        intent.putExtra(FLAG, "terms");
        startActivity(intent);
    }
}