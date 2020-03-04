package kamil.michalski.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import kamil.michalski.todolist.model.TodoTask;


public class TodoDbOpenHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 3;

    public TodoDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TodoTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if (oldVersion==1||oldVersion==2){
            database.execSQL("ALTER TABLE todo_task ADD COLUMN reminder BOOLEAN NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE todo_task ADD COLUMN reminderDate TEXT");
        }

    }
}
