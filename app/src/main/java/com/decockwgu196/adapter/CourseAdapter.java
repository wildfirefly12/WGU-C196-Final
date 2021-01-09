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
import com.example.wgu196final.model.Course;

import java.util.List;
import java.util.Objects;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private OnCourseClickListener courseClickListener;
    private List<Course> courseList;
    private Context context;

    public CourseAdapter(List<Course> courseList, Context context, OnCourseClickListener courseClickListener) {
        this.courseClickListener = courseClickListener;
        this.courseList = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.course_listitem), parent, false);
        ViewHolder holder = new ViewHolder(view, courseClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = Objects.requireNonNull(courseList.get(position));

        holder.title.setText(course.getTitle());
        holder.startDate.setText((course.getStartDate()));
        holder.endDate.setText(course.getEndDate());
        holder.status.setText(course.getStatus());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener {
        OnCourseClickListener onCourseClickListener;

        TextView title;
        TextView startDate;
        TextView endDate;
        TextView status;
        RelativeLayout courseList;

        public ViewHolder(@NonNull View itemView, OnCourseClickListener onCourseClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.courseTitleText);
            startDate = itemView.findViewById(R.id.courseStartDateText);
            endDate = itemView.findViewById(R.id.courseEndDateText);
            status = itemView.findViewById(R.id.courseStatus);

            this.onCourseClickListener = onCourseClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCourseClickListener.onCourseClick(getAdapterPosition());
        }
    }


    public interface  OnCourseClickListener{
        void onCourseClick(int position);
    }
}
