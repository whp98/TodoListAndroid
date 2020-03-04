package com.hdu.todolist.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.hdu.todolist.Adapter.HiddenTaskAdapter;
import com.hdu.todolist.NotificationsPlanner;
import com.hdu.todolist.R;
import com.hdu.todolist.database.ITaskDataBase;
import com.hdu.todolist.database.SqliteTaskDatabase;
import com.hdu.todolist.model.TodoTask;

/**
 * 展示和提示找回已经完成的任务,当用户点击任务的时候，提示并且将被点击的任务修改为没有隐藏的状态
 */
public class ShowHiddenTaskActivity extends AppCompatActivity implements HiddenTaskAdapter.OnClickListener {
    private ITaskDataBase mTaskDatabase;
    private HiddenTaskAdapter mAdapter;
    @BindView(R.id.hidden_task_list)
    RecyclerView mTodoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hidden_task);
        setTitle(getString(R.string.Show_hiden_title));
        mTaskDatabase = new SqliteTaskDatabase(this);
        ButterKnife.bind(this);
        mTodoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HiddenTaskAdapter(mTaskDatabase.getTasksHid(),this);
        mTodoList.setAdapter(mAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void refreshListData() {
        mAdapter.setmTask(mTaskDatabase.getTasksHid());
    }
    /////////////////
    @Override
    protected void onResume() {
        super.onResume();
        refreshListData();
        new NotificationsPlanner(mTaskDatabase, this).planNotifications();
    }
    /////////////////
    @Override
    public void onClick(TodoTask task, int position) {
        // 啥都不做
    }

    @Override
    public void onTaskHiddenChanged(TodoTask task, int position, boolean isHidden) {
        task.setHiden(isHidden);
        task.setDateCreated(new Date());
        mTaskDatabase.updateTask(task, position);
        refreshListData();
    }
    //彻底删除task
    @Override
    public void onTaskDeleteChanged(TodoTask task, int position, boolean isHidden) {
        task.setDateCreated(new Date());
        mTaskDatabase.deleteTask(task,position);
        refreshListData();
    }

    //返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
