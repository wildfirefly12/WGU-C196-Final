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
import com.decockwgu196.model.Assessment;

import java.util.List;
import java.util.Objects;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {
    private OnAssessmentClickListener assessmentClickListener;
    private List<Assessment> assessmentList;
    private Context context;

    public AssessmentAdapter(List<Assessment> assessmentList, Context context, OnAssessmentClickListener assessmentClickListener){
        this.assessmentList = assessmentList;
        this.context = context;
        this.assessmentClickListener = assessmentClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.assessment_list_item), parent, false);
        ViewHolder holder = new ViewHolder(view, assessmentClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assessment assessment = Objects.requireNonNull(assessmentList.get(position));

        holder.title.setText(assessment.getTitle());
        holder.start.setText(assessment.getStartDate());
        holder.end.setText(assessment.getEndDate());
    }


    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnAssessmentClickListener onAssessmentClickListener;

        TextView title;
        TextView start;
        TextView end;
        RelativeLayout assessmentList;

        public ViewHolder(@NonNull View itemView, OnAssessmentClickListener onAssessmentClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.assessment_view_title);
            start = itemView.findViewById(R.id.assessment_view_start);
            end = itemView.findViewById(R.id.assessment_view_end);
            assessmentList = itemView.findViewById(R.id.course_view_assessments);

            this.onAssessmentClickListener = onAssessmentClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssessmentClickListener.onAssessmentClick(getAdapterPosition());
        }
    }

    public interface OnAssessmentClickListener{
        void onAssessmentClick(int position);
    }
}
