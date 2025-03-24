package com.sunpra.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sunpra.classroom.data.AppDatabase;
import com.sunpra.classroom.databinding.ActivityAddStudentBinding;
import com.sunpra.classroom.databinding.ActivityStudentListBinding;
import com.sunpra.classroom.model.Student;
import com.sunpra.classroom.model.StudentDao;
import com.sunpra.classroom.model.StudentWithSubjects;

import java.util.List;
import java.util.concurrent.Executors;

public class StudentListActivity extends AppCompatActivity implements StudentMenuClickListener {

    ActivityResultLauncher<Intent> updateStudentResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (result) -> {
                //TODO: will receive result here.
            }
    );
    ActivityStudentListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addStudentMenu) {
            // Navigate to add student activity.
            Intent intent = new Intent(
                    StudentListActivity.this, AddStudentActivity.class
            );
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void initializeView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.all_students));
        }
        updateStudents();
    }

    private void updateStudents() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // get list of students from database.
            List<StudentWithSubjects> students = AppDatabase.getInstance(this).studentDao().getAll();
            // Initialize recycler view adapter.
            StudentListAdapter studentListAdapter = new StudentListAdapter(
                    students,
                    StudentListActivity.this
            );
            // Pass adapter ro recycler view.
            runOnUiThread(() -> {
                binding.studentRV.setAdapter(studentListAdapter);
                binding.studentRV.setLayoutManager(new LinearLayoutManager(this));
            });
        });
    }

    @Override
    public void onDeleteClicked(StudentWithSubjects studentWithSubjects) {
        Executors.newSingleThreadExecutor().execute(() -> {
            StudentDao studentDao = AppDatabase.getInstance(StudentListActivity.this).studentDao();
            studentDao.deleteStudent(studentWithSubjects.student);
            updateStudents();
        });
    }

    @Override
    public void onEditClicked(StudentWithSubjects studentWithSubjects) {
        //TODO handle edit student
        Intent intent = new Intent(StudentListActivity.this, AddStudentActivity.class);
        intent.putExtra(AddStudentActivity.EXTRA_STUDENT_WITH_SUBJECTS, studentWithSubjects);
        updateStudentResultLauncher.launch(intent);
    }
}