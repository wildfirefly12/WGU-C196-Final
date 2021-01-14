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
import com.decockwgu196.model.Note;

import java.util.List;
import java.util.Objects;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private OnNoteClickListener noteClickListener;
    private List<Note> noteList;
    private Context context;

    public NoteAdapter(List<Note> noteList, Context context, OnNoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
        this.noteList = noteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.note_list_item), parent, false);
        ViewHolder holder = new ViewHolder(view, noteClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = Objects.requireNonNull(noteList.get(position));

        holder.title.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private OnNoteClickListener onNoteClickListener;

        TextView title;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.note_view_title);
            relativeLayout = itemView.findViewById(R.id.note_list);

            this.onNoteClickListener = onNoteClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteClickListener{
        void onNoteClick(int position);
    }
}
