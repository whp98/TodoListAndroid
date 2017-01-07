package kamil.michalski.todolist;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MemoryTaskDatabase implements ITaskDataBase {
    private static List<TodoTask> mTasks = new LinkedList<>();

    @Override
    public List<TodoTask> getTasks() {
        return Collections.unmodifiableList(mTasks);
    }

    @Override
    public void addTask(TodoTask task) {
        mTasks.add(task);
    }

    @Override
    public TodoTask getTask(int position) {
        return mTasks.get(position);
    }

    @Override
    public void updateTask(TodoTask task, int position) {
        mTasks.set(position,task);
    }
}
