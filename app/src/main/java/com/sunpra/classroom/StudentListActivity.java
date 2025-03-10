package com.sunpra.classroom;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sunpra.classroom.data.AppDatabase;
import com.sunpra.classroom.databinding.ActivityAddStudentBinding;
import com.sunpra.classroom.databinding.ActivityStudentListBinding;
import com.sunpra.classroom.model.Student;

import java.util.List;
import java.util.concurrent.Executors;

public class StudentListActivity extends AppCompatActivity {

    ActivityStudentListBinding binding; //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentListBinding.inflate(getLayoutInflater()); //TODO
        setContentView(binding.getRoot()); // TODO
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeView();
    }

    private void initializeView() {
        updateStudents();
    }

    private void updateStudents(){
        Executors.newSingleThreadExecutor().execute(() -> {
            // get list of students from database.
            List<Student> students = AppDatabase.getInstance(this).studentDao().getAll();
            // Initialize recycler view adapter.
            StudentListAdapter studentListAdapter = new StudentListAdapter(students);
            // Pass adapter ro recycler view.
            runOnUiThread(() -> {
                binding.studentRV.setAdapter(studentListAdapter);
                binding.studentRV.setLayoutManager(new LinearLayoutManager(this));
            });
        });
    }
}