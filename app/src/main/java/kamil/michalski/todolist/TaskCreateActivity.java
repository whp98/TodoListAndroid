package kamil.michalski.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskCreateActivity extends AppCompatActivity {

    private ITaskDataBase mTaskDatabase = new MemoryTaskDatabase();
    @BindView(R.id.task_title)
    EditText mTaskTitle;
    @BindView(R.id.task_note)
    EditText mTaskNote;

    private int mPosition = -1;
    private TodoTask mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        ButterKnife.bind(this);
        //sprawdzamy czy chcemy cos edytowac
        //jezeli tak
        if (getIntent().hasExtra("pos")) {
            mPosition = getIntent().getIntExtra("pos", -1);
            mTask = mTaskDatabase.getTask(mPosition);

            mTaskTitle.setText(mTask.getName());
            mTaskNote.setText(mTask.getNote());
        }
        //wywoluje strzalke
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @OnClick(R.id.btn_save)
    void OnSaveClick() {
        //jezeli  mTask jest rozny od null bierzemy wartosc po lewej w przeciwnym wypdaku po prawej, azeby nie stracic  informacji zapisanych wczesniej
        TodoTask task = mTask != null ? mTask : new TodoTask();
        task.setDateCreated(new Date());
        task.setName(mTaskTitle.getText().toString());
        task.setNote(mTaskNote.getText().toString());
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
