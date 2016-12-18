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
import butterknife.OnClick;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.TodoViewHolder> {
    private List<TodoTask> mTask;
    private OnClickListener mClickListener;

    public TodoTaskAdapter(List<TodoTask> mTask, OnClickListener mClickListener) {
        this.mTask = mTask;
        this.mClickListener = mClickListener;
    }

    public void setmTask(List<TodoTask> mTask) {
        this.mTask = mTask;
        notifyDataSetChanged();
    }

    //funkcja zawsze wyglada podobnie

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //przeksztalca XML na obiekt klasy view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(rowView);

    }

    //ustawia liste na pozycji
    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        //pobranie elementu danyc na pozycji position
        TodoTask task = mTask.get(position);
        //uzupelnienie widoku (holder) na podstawie pobranego obiektu
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.getDone());
        holder.mCurrentPosition = position;
        holder.mCurrentTask = task;


    }

    //ile elementow mam wyswietlic
    @Override
    public int getItemCount() {
        return mTask.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_done)
        CheckBox mDone;
        @BindView(R.id.task_title)
        TextView mTitle;

        TodoTask mCurrentTask;
        int mCurrentPosition;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onItemClick() {
            if (mClickListener != null) {
                mClickListener.onClick(mCurrentTask, mCurrentPosition);
            }
        }
    }


    public interface OnClickListener {
        void onClick(TodoTask task, int position);
    }
}
