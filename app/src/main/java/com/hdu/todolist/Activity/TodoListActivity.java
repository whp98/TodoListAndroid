package com.hdu.todolist.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.hdu.todolist.Adapter.TodoTaskAdapter;
import com.hdu.todolist.NotificationsPlanner;
import com.hdu.todolist.R;
import com.hdu.todolist.database.ITaskDataBase;
import com.hdu.todolist.database.SqliteTaskDatabase;
import com.hdu.todolist.model.TodoTask;
/**
 *  添加任务页面
 */
public class TodoListActivity extends AppCompatActivity implements TodoTaskAdapter.OnClickListener {
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
        setTitle(getString(R.string.task_act_menu));
        mTodoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoTaskAdapter(mTaskDatabase.getTasks(), this);
        mTodoList.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListData();
        new NotificationsPlanner(mTaskDatabase, this).planNotifications();
    }

    private void refreshListData() {
        mAdapter.setmTask(mTaskDatabase.getTasks());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todolist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_create) {
            Intent createTaskIntent = new Intent(this, TaskCreateActivity.class);
            startActivity(createTaskIntent);
            return true;
        }
        if(item.getItemId() == R.id.hidden_task){
            Intent createTaskIntent = new Intent(this, ShowHiddenTaskActivity.class);
            startActivity(createTaskIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(TodoTask task, int position) {
        // Toast.makeText(this,"Klik "+position, Toast.LENGTH_SHORT).show();
        Intent createTaskIntent = new Intent(this, TaskCreateActivity.class);
        createTaskIntent.putExtra("pos", position);
        startActivity(createTaskIntent);
    }

    @Override
    public void onTaskDoneChanged(TodoTask task, int position, boolean isDone) {
        //Toast.makeText(this,"Done "+position+" isDone: "+isDone,Toast.LENGTH_LONG).show();
        task.setDone(isDone);
        task.setDateCreated(new Date());
        mTaskDatabase.updateTask(task, position);
        refreshListData();

    }

    @Override
    public void onTaskHiddenChanged(TodoTask task, int position, boolean isHidden) {
        task.setHiden(isHidden);
        task.setDateCreated(new Date());
        mTaskDatabase.updateTask(task, position);
        refreshListData();
    }
}
