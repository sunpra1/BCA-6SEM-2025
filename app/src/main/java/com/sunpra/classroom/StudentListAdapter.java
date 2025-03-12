package com.sunpra.classroom;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunpra.classroom.databinding.ItemStudentBinding;
import com.sunpra.classroom.model.Student;
import com.sunpra.classroom.model.StudentWithSubjects;
import com.sunpra.classroom.model.Subject;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<StudentWithSubjects> studentsWithSubjects;
    private StudentMenuClickListener studentMenuClickListener;

    StudentListAdapter(
            List<StudentWithSubjects> studentsWithSubjects,
            StudentMenuClickListener studentMenuClickListener
    ) {
        this.studentsWithSubjects = studentsWithSubjects;
        this.studentMenuClickListener = studentMenuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding binding = ItemStudentBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentWithSubjects studentWithSubject = studentsWithSubjects.get(position);
        holder.updateStudentDetails(studentWithSubject);
    }

    @Override
    public int getItemCount() {
        return studentsWithSubjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemStudentBinding itemStudentBinding;


        public ViewHolder(@NonNull ItemStudentBinding itemView) {
            super(itemView.getRoot());
            this.itemStudentBinding = itemView;
        }

        void updateStudentDetails(StudentWithSubjects studentWithSubjects) {
            itemStudentBinding.studentName.setText(studentWithSubjects.student.getName());
            itemStudentBinding.studentGender.setText(studentWithSubjects.student.getGender().toString());
            itemStudentBinding.studentGrade.setText(studentWithSubjects.student.getGrade().toString());
            itemStudentBinding.studentEnrolled.setText(String.valueOf(studentWithSubjects.student.isEnrolled()));
            StringBuilder optionalSubjects = new StringBuilder();
            for (int i = 0; i < studentWithSubjects.subjects.size(); i++) {
                Subject subject = studentWithSubjects.subjects.get(i);
                optionalSubjects.append(subject.getSubjectName());
                if (i < studentWithSubjects.subjects.size() - 1) {
                    optionalSubjects.append(", ");
                }
            }
            itemStudentBinding.optionalSubjects.setText(optionalSubjects);
            itemStudentBinding.moreOptions.setOnClickListener((view) -> {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.deleteStudent) {
                        studentMenuClickListener.onDeleteClicked(studentWithSubjects);
                        return true;
                    }
                    return false;
                });
                popup.inflate(R.menu.student_litem_menu);
                popup.show();
            });
        }
    }
}

interface StudentMenuClickListener {
    void onDeleteClicked(StudentWithSubjects studentWithSubjects);
}



