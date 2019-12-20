package kamil.michalski.todolist.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.nio.Buffer;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kamil.michalski.todolist.Adapter.HiddenTaskAdapter;
import kamil.michalski.todolist.NotificationsPlanner;
import kamil.michalski.todolist.R;
import kamil.michalski.todolist.database.ITaskDataBase;
import kamil.michalski.todolist.database.SqliteTaskDatabase;
import kamil.michalski.todolist.model.TodoTask;

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
}
