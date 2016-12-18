package kamil.michalski.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoListActivity extends AppCompatActivity {
    @BindView(R.id.task_list)
    RecyclerView mTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);

        //1.w jakim ukladzie maja wyswietlac sie elementy listy
        //uklad pionowy - 1  element na wiersz
        mTodoList.setLayoutManager(new LinearLayoutManager(this));

        List<TodoTask> tasks = new LinkedList<>();
        TodoTask task = new TodoTask();
        task.setName("zadanie 1");
        tasks.add(task);


        task = new TodoTask();
        task.setDone(true);
        task.setName("zadanie 2");
        tasks.add(task);


        TodoTaskAdapter adapter = new TodoTaskAdapter(tasks);
        mTodoList.setAdapter(adapter);

        //mTodoList.setAdapter(listAdapter);
    }
}
