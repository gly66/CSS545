package com.example.statemanagement;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            tasks = savedInstanceState.getStringArrayList("TASKS_LIST");
        } else {
            tasks = new ArrayList<>();
            // Populate the list with sample tasks
            for (int i = 1; i <= 20; i++) {
                tasks.add("Task " + i);
            }
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(tasks, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("TASKS_LIST", new ArrayList<>(tasks));
    }

    @Override
    public void onTaskClick(int position) {
        // Implement navigation to a details activity or fragment
    }
}
