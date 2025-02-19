package com.sunpra.classroom;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sunpra.classroom.databinding.ActivityAddStudentBinding;

public class AddStudentActivity extends AppCompatActivity
        implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    ActivityAddStudentBinding binding; //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater()); //TODO
        setContentView(binding.getRoot()); //TODO
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeView();
    }

    private void initializeView() {
        binding.addBtn.setOnClickListener(AddStudentActivity.this);
        binding.genderGroup.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.gradeSpinner.setOnItemClickListener(AddStudentActivity.this);
        binding.optSubjectAccounts.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectMath.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectComputer.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectEconomics.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.isEnrolled.setOnCheckedChangeListener(AddStudentActivity.this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}