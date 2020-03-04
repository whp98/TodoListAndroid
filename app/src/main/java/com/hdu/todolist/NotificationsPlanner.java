package com.hdu.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Date;
import java.util.List;

import com.hdu.todolist.database.ITaskDataBase;
import com.hdu.todolist.model.TodoTask;
import com.hdu.todolist.service.TodoNotificationService;

/**
 *
 */

public class NotificationsPlanner {
    private ITaskDataBase mTaskDatabase;
    private Context mContext;

    public NotificationsPlanner(ITaskDataBase mTaskDatabase, Context mContext) {
        this.mTaskDatabase = mTaskDatabase;
        this.mContext = mContext;
    }

    public void planNotifications() {
        List<TodoTask> tasks = mTaskDatabase.getFutureTasksWithReminder(new Date());
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        for (TodoTask task : tasks) {
            Intent serviceIntent = new Intent(mContext, TodoNotificationService.class);
            serviceIntent.putExtra("id", task.getId());
            PendingIntent pendingIntent = PendingIntent.getService(mContext,
                    task.getId(),
                    serviceIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        task.getReminderDate().getTime(),
                        pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        task.getReminderDate().getTime(),
                        pendingIntent);
            }

        }
    }
}
