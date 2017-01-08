package kamil.michalski.todolist.database;

import java.util.Date;
import java.util.List;

import kamil.michalski.todolist.model.TodoTask;

public interface ITaskDataBase {
    List<TodoTask> getTasks();

    List<TodoTask> getFutureTasksWithReminder(Date now);

    void addTask(TodoTask task);

    //pobieranie zadania wzgledem pozycji
    TodoTask getTask(int position);

    //podmienianie  zadania po edycji
    void updateTask(TodoTask task, int position);

}
