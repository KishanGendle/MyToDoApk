package com.example.mytodoapk.Model;

public class ToDoModel extends TaskId{
    private String task,dueDate,dueTime;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public int getStatus() {
        return status;
    }
}
