package kamil.michalski.todolist.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import kamil.michalski.todolist.R;
import kamil.michalski.todolist.model.TodoTask;

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


    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(rowView);

    }


    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoTask task = mTask.get(position);
        holder.mBlockListeners=true;
        holder.mCurrentPosition = task.getId();
        holder.mCurrentTask = task;
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.getDone());
        if (task.getDone()){
            holder.mHidden.setVisibility(View.VISIBLE);
        }else {
            holder.mHidden.setVisibility(View.INVISIBLE);
        }
        holder.mHidden.setChecked(task.isHiden());
        holder.mBlockListeners=false;
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
        @BindView(R.id.todoitem_hiddden)
        CheckBox mHidden;
        TodoTask mCurrentTask;
        int mCurrentPosition;
        boolean mBlockListeners = true;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }



        @OnClick
        void onItemClick() {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onClick(mCurrentTask, mCurrentPosition);
            }
        }

        @OnCheckedChanged(R.id.task_done)
        void onCheckedChange(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskDoneChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }

        @OnCheckedChanged(R.id.todoitem_hiddden)
        void onCheckedChange1(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskHiddenChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }
    }


    public interface OnClickListener {
        void onClick(TodoTask task, int position);
        void onTaskHiddenChanged(TodoTask task,int position,boolean isHidden);
        void onTaskDoneChanged(TodoTask task, int position, boolean isDone);
    }
}
