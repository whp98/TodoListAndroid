package com.hdu.todolist.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.hdu.todolist.R;
import com.hdu.todolist.database.ITaskDataBase;
import com.hdu.todolist.database.SqliteTaskDatabase;
import com.hdu.todolist.model.TodoTask;


public class TodoNotificationService extends IntentService {
    private ITaskDataBase mTaskDatabase;
    public TodoNotificationService() {
        super("TodoNotificationService");
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mTaskDatabase = new SqliteTaskDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int taskId = intent.getIntExtra("id", -1);
        TodoTask task=mTaskDatabase.getTask(taskId);

        if (task==null){return;}

        Notification  notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(mTaskDatabase.getTask(taskId).getName())
                .setContentText(mTaskDatabase.getTask(taskId).getNote())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setTicker(task.getName())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(taskId,notification);
    }

}
