package kamil.michalski.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoListActivity extends AppCompatActivity  implements TodoTaskAdapter.OnClickListener {
    @BindView(R.id.task_list)
    RecyclerView mTodoList;
    private ITaskDataBase mTaskDatabase;
    private TodoTaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        mTaskDatabase = new SqliteTaskDatabase(this);

        //1.w jakim ukladzie maja wyswietlac sie elementy listy
        //uklad pionowy - 1  element na wiersz
        mTodoList.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new TodoTaskAdapter(mTaskDatabase.getTasks(), this);
        mTodoList.setAdapter(mAdapter);

        //mTodoList.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListData();

    }

    private void refreshListData() {
        mAdapter.setmTask(mTaskDatabase.getTasks());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todolist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_item_create){
            Intent createTaskIntent = new Intent(this,TaskCreateActivity.class);
            startActivity(createTaskIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(TodoTask task, int position) {
       // Toast.makeText(this,"Klik "+position, Toast.LENGTH_SHORT).show();
        Intent createTaskIntent = new Intent(this,TaskCreateActivity.class);
        createTaskIntent.putExtra("pos",position);
        startActivity(createTaskIntent);
        //intent  do otwarcia formularza edycji taska
    }

    @Override
    public void onTaskDoneChanged(TodoTask task, int position, boolean isDone) {
        //wyswietla aktualny status pola done
        //Toast.makeText(this,"Done "+position+" isDone: "+isDone,Toast.LENGTH_LONG).show();
        task.setDone(isDone);
        task.setDateCreated(new Date());
        mTaskDatabase.updateTask(task,position);
        refreshListData();

    }
}
