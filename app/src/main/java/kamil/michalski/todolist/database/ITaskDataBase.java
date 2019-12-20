package kamil.michalski.todolist.database;

import java.util.Date;
import java.util.List;

import kamil.michalski.todolist.model.TodoTask;

public interface ITaskDataBase {
    List<TodoTask> getTasks();

    List<TodoTask> getFutureTasksWithReminder(Date now);

    void addTask(TodoTask task);

    TodoTask getTask(int position);

    void updateTask(TodoTask task, int position);

    void deleteTask(TodoTask task, int position);

    List<TodoTask> getTasksHid();
}
