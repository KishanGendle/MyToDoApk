package com.example.mytodoapk.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapk.AddNewTask;
import com.example.mytodoapk.MainActivity;
import com.example.mytodoapk.Model.ToDoModel;
import com.example.mytodoapk.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>{
    private final List<ToDoModel> todoList;
    private final MainActivity activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(MainActivity mainActivity,List<ToDoModel> todoList){
        this.todoList = todoList;
        activity = mainActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task,parent,false);
        firestore=FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }
    public void deleteTask(int position){
        ToDoModel toDoModel = todoList.get(position);
        firestore.collection("task").document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemChanged(position);
    }
    public Context getContext(){
        return activity;
    }

    public void editTask(int position){
        ToDoModel toDoModel = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("task", toDoModel.getTask());
        bundle.putString("dueDate",toDoModel.getDueDate());
        bundle.putString("dueTime",toDoModel.getDueTime());
        bundle.putString("id",toDoModel.TaskId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(),addNewTask.getTag());
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());
        holder.mDueDateTv.setText("Date: "+toDoModel.getDueDate());
        holder.mDueTimeTv.setText("Time: "+toDoModel.getDueTime());
        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                firestore.collection("task").document(toDoModel.TaskId).update("status",1);
                Toast.makeText(activity, "Task Completed", Toast.LENGTH_SHORT).show();
            }else{
                firestore.collection("task").document(toDoModel.TaskId).update("status",0);
                Toast.makeText(activity, "Task Rescheduled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean toBoolean(int status){
        return status !=0;
    }

    @Override
    public int getItemCount() {

        return todoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mDueDateTv,mDueTimeTv;
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDueDateTv=itemView.findViewById(R.id.due_date_tv);
            mDueTimeTv=itemView.findViewById(R.id.due_time_tv);
            mCheckBox=itemView.findViewById(R.id.mcheckbox);
        }
    }
}
