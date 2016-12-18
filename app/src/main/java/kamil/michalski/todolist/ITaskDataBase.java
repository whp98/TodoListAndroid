package kamil.michalski.todolist;

import java.util.List;

public interface ITaskDataBase {
    List<TodoTask> getTasks();

    void addTask(TodoTask task);
}
