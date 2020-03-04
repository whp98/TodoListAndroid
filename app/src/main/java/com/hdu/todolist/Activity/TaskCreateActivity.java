package com.hdu.todolist.Activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.hdu.todolist.database.ITaskDataBase;
import com.hdu.todolist.R;
import com.hdu.todolist.database.SqliteTaskDatabase;
import com.hdu.todolist.model.TodoTask;

/**
 * 显示task页面
 * 包含一个个的任务
 */
public class TaskCreateActivity extends AppCompatActivity {

    @BindView(R.id.task_title)
    EditText mTaskTitle;
    @BindView(R.id.task_note)
    EditText mTaskNote;
    @BindView(R.id.task_reminder)
    CheckBox mTaskReminder;
    @BindView(R.id.task_reminder_data)
    DatePicker mTaskReminderDate;
    @BindView(R.id.task_reminder_time)
    TimePicker mTaskReminderTime;
    private ITaskDataBase mTaskDatabase;
    private int mPosition = -1;
    private TodoTask mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        setTitle(getString(R.string.newtask_act_title));
        ButterKnife.bind(this);
        mTaskDatabase = new SqliteTaskDatabase(this);
        if (getIntent().hasExtra("pos")) {
            mPosition = getIntent().getIntExtra("pos", -1);
            mTask = mTaskDatabase.getTask(mPosition);

            mTaskTitle.setText(mTask.getName());
            mTaskNote.setText(mTask.getNote());
            if (mTask.isReminder()) {
                mTaskReminder.setChecked(true);
                Calendar reminderCalendar = Calendar.getInstance();
                reminderCalendar.setTime(mTask.getReminderDate());
                mTaskReminderDate.init(reminderCalendar.get(Calendar.YEAR),
                        reminderCalendar.get(Calendar.MONTH),
                        reminderCalendar.get(Calendar.DAY_OF_MONTH),
                        null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTaskReminderTime.setHour(reminderCalendar.get(Calendar.HOUR_OF_DAY));
                    mTaskReminderTime.setMinute(reminderCalendar.get(Calendar.MINUTE));
                } else {
                    mTaskReminderTime.setCurrentHour(reminderCalendar.get(Calendar.HOUR_OF_DAY));
                    mTaskReminderTime.setCurrentMinute(reminderCalendar.get(Calendar.MINUTE));
                }
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @OnCheckedChanged(R.id.task_reminder)
    void onRemiderChecked(boolean checked) {
        mTaskReminderDate.setVisibility(checked ? View.VISIBLE : View.GONE);
        mTaskReminderTime.setVisibility(checked ? View.VISIBLE : View.GONE);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @OnClick(R.id.btn_save)
    void OnSaveClick() {

        TodoTask task = mTask != null ? mTask : new TodoTask();
        task.setDateCreated(new Date());
        task.setName(mTaskTitle.getText().toString());
        task.setNote(mTaskNote.getText().toString());
        task.setReminder(mTaskReminder.isChecked());
        task.setHiden(false);
        if (task.isReminder()) {



            int hour = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                    mTaskReminderTime.getHour() :
                    mTaskReminderTime.getCurrentHour();


            int minute = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                    mTaskReminderTime.getMinute() :
                    mTaskReminderTime.getCurrentMinute();

            Calendar reminderCalendar = Calendar.getInstance();
            reminderCalendar.setTimeInMillis(0);
            reminderCalendar.set(mTaskReminderDate.getYear(),
                    mTaskReminderDate.getMonth(),
                    mTaskReminderDate.getDayOfMonth(), hour, minute);

            if (reminderCalendar.before(Calendar.getInstance())) {
                Toast.makeText(this, getString(R.string.create_time_passed), Toast.LENGTH_LONG).show();
                return;
            }


            task.setReminderDate(reminderCalendar.getTime());
        }
        if (mPosition == -1) {
            mTaskDatabase.addTask(task);
        } else {
            mTaskDatabase.updateTask(task, mPosition);
        }

        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
