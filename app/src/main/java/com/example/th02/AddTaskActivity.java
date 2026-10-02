package com.example.th02;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTaskTitle, etTaskNote;
    private Button btnPickDateTime, btnSaveTask;
    private String selectedDateTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskNote = findViewById(R.id.etTaskNote);
        btnPickDateTime = findViewById(R.id.btnPickDateTime);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        // Xử lý sự kiện bấm nút chọn Ngày & Giờ
        btnPickDateTime.setOnClickListener(v -> showDateTimePicker());

        // Xử lý sự kiện bấm nút Thêm mới
        btnSaveTask.setOnClickListener(v -> {
            String title = etTaskTitle.getText().toString().trim();
            String note = etTaskNote.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedDateTime.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn thời gian!", Toast.LENGTH_SHORT).show();
                return;
            }

            Task newTask = new Task(title, selectedDateTime, note);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_TASK", newTask);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Lịch chọn Ngày / Tháng / Năm
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Đồng hồ chọn Giờ : Phút
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                selectedDateTime = String.format(Locale.getDefault(), "%02d/%02d/%04d %02d:%02d",
                        selectedDay, selectedMonth + 1, selectedYear, selectedHour, selectedMinute);
                btnPickDateTime.setText(selectedDateTime);
            }, hour, minute, true);

            timePickerDialog.show();
        }, year, month, day);

        datePickerDialog.show();
    }
}