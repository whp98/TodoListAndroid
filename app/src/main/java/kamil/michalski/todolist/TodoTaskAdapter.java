package kamil.michalski.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.TodoViewHolder> {
    private List<TodoTask> mTask;

    public TodoTaskAdapter(List<TodoTask> mTask) {
        this.mTask = mTask;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //przeksztalca XML na obiekt klasy view
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View  rowView  = inflater.inflate(R.layout.list_item_todo,parent,false);
        return new TodoViewHolder(rowView);

    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        //pobranie elementu danyc na pozycji position
        TodoTask task=mTask.get(position);
        //uzupelnienie widoku (holder) na podstawie pobranego obiektu
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.getDone());


    }

    @Override
    public int getItemCount() {
       return mTask.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_done)
        CheckBox mDone;
        @BindView(R.id.task_title)
        TextView mTitle;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
