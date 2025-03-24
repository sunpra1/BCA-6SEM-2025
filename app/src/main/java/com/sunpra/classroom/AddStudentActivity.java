package com.sunpra.classroom;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sunpra.classroom.data.AppDatabase;
import com.sunpra.classroom.databinding.ActivityAddStudentBinding;
import com.sunpra.classroom.model.Grade;
import com.sunpra.classroom.model.OptionalSubject;
import com.sunpra.classroom.model.Student;
import com.sunpra.classroom.model.StudentDao;
import com.sunpra.classroom.model.StudentWithSubjects;
import com.sunpra.classroom.model.Subject;
import com.sunpra.classroom.model.SubjectDao;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddStudentActivity extends AppCompatActivity
        implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "AddStudentActivity";

    static final String EXTRA_STUDENT_WITH_SUBJECTS = "student_with_subjects";

    ActivityAddStudentBinding binding;

    Grade[] grades = {
            Grade.ONE,
            Grade.TWO,
            Grade.THREE,
            Grade.FOUR,
            Grade.FIVE,
            Grade.SIX,
            Grade.SEVEN,
            Grade.EIGHT,
            Grade.NINE,
            Grade.TEN
    };
    Grade selectedGrade;
    Gender selectedGender;
    Set<OptionalSubject> selectedOptionalSubjects = new ArraySet<>();
    boolean isEnrolled;

    StudentWithSubjects studentWithSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_STUDENT_WITH_SUBJECTS)){
            studentWithSubjects = (StudentWithSubjects) intent.getSerializableExtra(EXTRA_STUDENT_WITH_SUBJECTS);
        }
        initializeView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void initializeView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.add_student));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        binding.addBtn.setOnClickListener(AddStudentActivity.this);
        binding.genderGroup.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.gradeSpinner.setOnItemSelectedListener(AddStudentActivity.this);
        binding.optSubjectAccounts.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectMath.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectComputer.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectEconomics.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.isEnrolled.setOnCheckedChangeListener(AddStudentActivity.this);

        if(studentWithSubjects != null){
            //Note: We are here for edit
            binding.studentName.setText(studentWithSubjects.student.getName());

            // TODO: binding.genderGroup

            //region grade
            int selectedIndex = -1;
            for(int index = 0; index < grades.length; index++){
                if(grades[index] == studentWithSubjects.student.getGrade()){
                    selectedIndex = index;
                    break;
                }
            }
            if(selectedIndex > -1){
                binding.gradeSpinner.setSelection(selectedIndex);
            }
            //endregion

            //region Optional Subject
            boolean isOptSubjectAccountSelected = false;
            boolean isOptSubjectMathSelected = false;
            boolean isOptSubjectComputerSelected = false;
            boolean isOptSubjectEconomicsSelected = false;

            for(Subject subject : studentWithSubjects.subjects){
                if(
                        subject.getSubjectName()
                                .equals(OptionalSubject.ACCOUNTS.name())
                ){
                    isOptSubjectAccountSelected = true;
                }else if(
                        subject.getSubjectName()
                                .equals(OptionalSubject.OPTIONAL_MATH.name())
                ){
                    isOptSubjectMathSelected = true;
                }else if(
                        subject.getSubjectName()
                                .equals(OptionalSubject.COMPUTER.name())
                ){
                    isOptSubjectComputerSelected = true;
                }else if(
                        subject.getSubjectName()
                                .equals(OptionalSubject.ECONOMICS.name())
                ){
                    isOptSubjectEconomicsSelected = true;
                }
            }

            binding.optSubjectAccounts.setChecked(
                    studentWithSubjects.subjects.contains()
            );
            binding.optSubjectMath.setChecked();
            binding.optSubjectComputer.setChecked();
            binding.optSubjectEconomics.setChecked();
            //endregion
        }

        initializeGradeAdapter();
    }

    void initializeGradeAdapter() {
        ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter<>(
                AddStudentActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                grades
        ) {
            @Override
            public View getDropDownView(
                    int position,
                    @Nullable View convertView,
                    @NonNull ViewGroup parent
            ) {
                CheckedTextView dropDownView = (CheckedTextView) super.getDropDownView(position, convertView, parent);
                if (binding.gradeSpinner.getSelectedItemPosition() == position)
                    dropDownView.setTextColor(getColor(R.color.green));
                return dropDownView;
            }
        };
        binding.gradeSpinner.setAdapter(gradeAdapter);
    }

    private void showMessage(String message) {
        new AlertDialog.Builder(AddStudentActivity.this)
                .setTitle(getString(R.string.message))
                .setMessage(message)
                .show();
    }

    private boolean validateUserName() {
        boolean isValid = true;

        if (binding.studentName.toString().isEmpty()) {
            isValid = false;
            showMessage(getString(R.string.student_name_required));
        }

        return isValid;
    }

    private boolean validateGender() {
        boolean isValid = true;

        if (selectedGender == null) {
            isValid = false;
            showMessage(
                    getString(R.string.gender_is_required)
            );
        }

        return isValid;
    }

    private boolean validateGrade() {
        boolean isValid = true;

        if (selectedGrade == null) {
            isValid = false;
            showMessage(
                    getString(R.string.grade_is_required)
            );
        }

        return isValid;
    }

    private boolean validateOptionalSubjects() {
        boolean isValid = true;

        if (selectedOptionalSubjects.isEmpty()) {
            isValid = false;
            showMessage(
                    getString(R.string.select_optional_subjects)
            );
        } else if (selectedOptionalSubjects.size() != 2) {
            isValid = false;
            showMessage(
                    getString(R.string.two_optional_subject_not_selected)
            );
        }

        return isValid;
    }

    private boolean validate() {
        return validateUserName() &&
                validateGender() &&
                validateGrade() &&
                validateOptionalSubjects();
    }

    @Override
    public void onClick(View view) {
        if (validate()) {
            addStudent();
        }
    }

    private void addStudent() {
        String name = binding.studentName.getText().toString();
        Gender gender = this.selectedGender;
        Grade grade = this.selectedGrade;
        boolean isEnrolled = this.isEnrolled;

        Student student = new Student(
                0,
                name,
                gender,
                grade,
                isEnrolled
        );

        AppDatabase appDatabase = AppDatabase.getInstance(AddStudentActivity.this);

        StudentDao studentDao = appDatabase.studentDao();
        SubjectDao subjectDao = appDatabase.subjectDao();

        Executors.newSingleThreadExecutor().execute(() -> {
            long studentId = studentDao.insertStudent(student);
            for (OptionalSubject subject : selectedOptionalSubjects) {
                subjectDao.insert(
                        new Subject(
                                0,
                                subject.name(),
                                (int) studentId
                        )
                );
            }
            navigateToStudentListScreen();
        });
    }

    private void navigateToStudentListScreen() {
        Intent intent = new Intent(AddStudentActivity.this, StudentListActivity.class);
        startActivity(intent);
    }

    // For gender group
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (binding.genderMale.isChecked()) {
            selectedGender = Gender.MALE;
        } else if (binding.genderFemale.isChecked()) {
            selectedGender = Gender.FEMALE;
        } else if (binding.genderOthers.isChecked()) {
            selectedGender = Gender.OTHERS;
        } else {
            // will not reach here
            selectedGender = null;
        }
    }

    //For Optional Subjects Checkbox, and isEnrolled Switch
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.getId() == binding.optSubjectAccounts.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.ACCOUNTS);
            } else {
                selectedOptionalSubjects.remove(OptionalSubject.ACCOUNTS);
            }
        } else if (compoundButton.getId() == binding.optSubjectMath.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.OPTIONAL_MATH);
            } else {
                selectedOptionalSubjects.remove(OptionalSubject.OPTIONAL_MATH);
            }
        } else if (compoundButton.getId() == binding.optSubjectComputer.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.COMPUTER);
            } else {
                selectedOptionalSubjects.remove(OptionalSubject.COMPUTER);
            }
        } else if (compoundButton.getId() == binding.optSubjectEconomics.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.ECONOMICS);
            } else {
                selectedOptionalSubjects.remove(OptionalSubject.ECONOMICS);
            }
        } else if (compoundButton.getId() == binding.isEnrolled.getId()) {
            isEnrolled = isChecked;
        }
        Log.i(TAG, "onCheckedChanged: " + selectedOptionalSubjects);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedGrade = grades[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    } //Show toast message
}