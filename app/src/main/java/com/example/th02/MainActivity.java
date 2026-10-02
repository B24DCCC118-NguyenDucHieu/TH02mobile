package com.example.th02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_TASK_REQUEST_CODE = 100;

    private TextView tvUserName, tvUserId;
    private FloatingActionButton btnAddTask;
    private RecyclerView rvTodayTasks, rvUpcomingTasks;

    private List<Task> todayTaskList = new ArrayList<>();
    private List<Task> upcomingTaskList = new ArrayList<>();

    private TaskAdapter todayAdapter;
    private TaskAdapter upcomingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserId = findViewById(R.id.tvUserId);
        btnAddTask = findViewById(R.id.btnAddTask);
        rvTodayTasks = findViewById(R.id.rvTodayTasks);
        rvUpcomingTasks = findViewById(R.id.rvUpcomingTasks);

        String name = getIntent().getStringExtra("USER_NAME");
        String id = getIntent().getStringExtra("USER_ID");
        tvUserName.setText(name != null ? name : "Nguyễn Văn A");
        tvUserId.setText(id != null ? id : "doc123");

        todayAdapter = new TaskAdapter(todayTaskList);
        rvTodayTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTodayTasks.setAdapter(todayAdapter);

        upcomingAdapter = new TaskAdapter(upcomingTaskList);
        rvUpcomingTasks.setLayoutManager(new LinearLayoutManager(this));
        rvUpcomingTasks.setAdapter(upcomingAdapter);

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Task newTask = (Task) data.getSerializableExtra("NEW_TASK");
            if (newTask != null) {
                String todayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                String taskDate = newTask.getTime().split(" ")[0];

                if (taskDate.equals(todayDate)) {
                    todayTaskList.add(newTask);
                    todayAdapter.notifyDataSetChanged();
                } else {
                    upcomingTaskList.add(newTask);
                    upcomingAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}