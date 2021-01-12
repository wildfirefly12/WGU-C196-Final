package com.decockwgu196.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.decockwgu196.R;
import com.decockwgu196.model.Term;

import java.util.List;
import java.util.Objects;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {
    private  OnTermClickListener termClickListener;
    private List<Term> termList;
    private Context context;

    public TermsAdapter(List<Term> termList, Context context, OnTermClickListener termClickListener) {
        this.termList = termList;
        this.context = context;
        this.termClickListener = termClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, termClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Term term = Objects.requireNonNull(termList.get(position));

        holder.title.setText(term.getTitle());
        holder.startDate.setText(term.getStartDate());
        holder.endDate.setText(term.getEndDate());
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTermClickListener onTermClickListener;

        TextView title;
        TextView startDate;
        TextView endDate;
        RelativeLayout termList;

        public ViewHolder(@NonNull View itemView, OnTermClickListener onTermClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.termTitleText);
            startDate = itemView.findViewById(R.id.termStartDateText);
            endDate = itemView.findViewById(R.id.termEndDateText);
            termList = itemView.findViewById(R.id.termListItem);

            this.onTermClickListener = onTermClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTermClickListener.onTermClick(getAdapterPosition());
        }
    }

    public interface OnTermClickListener{
        void onTermClick(int position);
    }

}
