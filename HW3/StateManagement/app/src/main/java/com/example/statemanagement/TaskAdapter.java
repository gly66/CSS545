package com.example.statemanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<String> mTasks;
    private OnTaskListener mOnTaskListener;

    public TaskAdapter(List<String> tasks, OnTaskListener onTaskListener) {
        this.mTasks = tasks;
        this.mOnTaskListener = onTaskListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view, mOnTaskListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String task = mTasks.get(position);
        holder.taskName.setText(task);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskName;
        Button viewDetailsButton;
        OnTaskListener onTaskListener;

        public ViewHolder(View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            this.onTaskListener = onTaskListener;
            viewDetailsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }

    public interface OnTaskListener {
        void onTaskClick(int position);
    }
}
