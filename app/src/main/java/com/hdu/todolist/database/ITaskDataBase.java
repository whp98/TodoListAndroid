package com.hdu.todolist.database;

import java.util.Date;
import java.util.List;

import com.hdu.todolist.model.TodoTask;

public interface ITaskDataBase {
    List<TodoTask> getTasks();

    List<TodoTask> getTasksHid();

    List<TodoTask> getFutureTasksWithReminder(Date now);

    void addTask(TodoTask task);

    TodoTask getTask(int position);

    void updateTask(TodoTask task, int position);

    void deleteTask(TodoTask task, int position);
}
