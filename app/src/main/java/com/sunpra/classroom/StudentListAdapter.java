package com.sunpra.classroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunpra.classroom.databinding.ItemStudentBinding;
import com.sunpra.classroom.model.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<Student> students;

    StudentListAdapter(List<Student> students){
        this.students = students;
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
        Student student = students.get(position);
        holder.updateStudentDetails(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemStudentBinding itemStudentBinding;


        public ViewHolder(@NonNull ItemStudentBinding itemView) {
            super(itemView.getRoot());
            this.itemStudentBinding = itemView;
        }

        void updateStudentDetails(Student student){
            itemStudentBinding.studentName.setText(student.getName());
            itemStudentBinding.studentGender.setText(student.getGender().toString());
            itemStudentBinding.studentGrade.setText(student.getGrade().toString());
            itemStudentBinding.studentEnrolled.setText(String.valueOf(student.isEnrolled()));
        }
    }
}



