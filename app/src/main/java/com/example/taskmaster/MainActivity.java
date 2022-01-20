package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.taskmaster.AddTask.Add;
import com.example.taskmaster.AddTask.DatabaseHandler;
import com.example.taskmaster.AddTask.TaskModel;
import com.example.taskmaster.RecyclerView.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogClosedListener{

    private RecyclerView taskRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton fab;

    private List<TaskModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskRecyclerView = findViewById(R.id.tasksRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(db,MainActivity.this);
        taskRecyclerView.setAdapter(recyclerViewAdapter);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();
        recyclerViewAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add.newInstance().show(getSupportFragmentManager(), Add.TAG);
            }
        });

    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        recyclerViewAdapter.setTasks(taskList);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}