package kamil.michalski.todolist;

import java.util.List;

public interface ITaskDataBase {
    List<TodoTask> getTasks();

    void addTask(TodoTask task);
//pobieranie zadania wzgledem pozycji
    TodoTask getTask(int position);
//podmienianie  zadania po edycji
    void updateTask(TodoTask  task, int position);

}
