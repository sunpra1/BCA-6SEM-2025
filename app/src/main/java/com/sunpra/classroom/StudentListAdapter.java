package com.sunpra.classroom;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunpra.classroom.databinding.ItemStudentBinding;
import com.sunpra.classroom.model.Student;
import com.sunpra.classroom.model.StudentWithSubjects;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<StudentWithSubjects> studentsWithSubjects;

    StudentListAdapter(List<StudentWithSubjects> studentsWithSubjects){
        this.studentsWithSubjects = studentsWithSubjects;
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

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemStudentBinding itemStudentBinding;


        public ViewHolder(@NonNull ItemStudentBinding itemView) {
            super(itemView.getRoot());
            this.itemStudentBinding = itemView;
        }

        void updateStudentDetails(StudentWithSubjects studentWithSubjects){
            itemStudentBinding.studentName.setText(studentWithSubjects.student.getName());
            itemStudentBinding.studentGender.setText(studentWithSubjects.student.getGender().toString());
            itemStudentBinding.studentGrade.setText(studentWithSubjects.student.getGrade().toString());
            itemStudentBinding.studentEnrolled.setText(String.valueOf(studentWithSubjects.student.isEnrolled()));

            //TODO Task: add optional subjects.
        }
    }
}



