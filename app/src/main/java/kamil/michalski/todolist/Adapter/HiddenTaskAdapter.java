package kamil.michalski.todolist.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import kamil.michalski.todolist.R;
import kamil.michalski.todolist.model.TodoTask;

/**
 *  用于已经隐藏页面的显示和监听器设置
 *  点击某一个一个项目即可找回任务
 */
public class HiddenTaskAdapter extends RecyclerView.Adapter<HiddenTaskAdapter.Hidviewholder>{
    private List<TodoTask> mTask;
    private OnClickListener mClickListener;
    public HiddenTaskAdapter(List<TodoTask> mTask, OnClickListener mClickListener) {
        this.mTask = mTask;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public Hidviewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.hidden_task_item, parent, false);
        return new Hidviewholder(rowView);
    }

    //数据更新
    public void setmTask(List<TodoTask> mTask) {
        this.mTask = mTask;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull Hidviewholder holder, int position) {
        TodoTask task = mTask.get(position);
        holder.mBlockListeners=true;
        holder.mCurrentPosition = task.getId();
        holder.mCurrentTask = task;
        holder.mTitle.setText(task.getName());
        holder.hidtask.setChecked(task.isHiden());
        holder.delTask .setChecked(false);
        holder.mBlockListeners=false;
    }

    @Override
    public int getItemCount() {
        return mTask.size();
    }

    class Hidviewholder extends RecyclerView.ViewHolder{
        @BindView(R.id.hid_task_retrn)
        CheckBox hidtask;
        @BindView(R.id.hid_task_name)
        TextView mTitle;
        @BindView(R.id.hid_task_delete)
        CheckBox delTask;
        TodoTask mCurrentTask;
        int mCurrentPosition;
        boolean mBlockListeners = true;
        public Hidviewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }



        @OnClick
        void onItemClick() {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onClick(mCurrentTask, mCurrentPosition);
            }
        }

        @OnCheckedChanged(R.id.hid_task_retrn)
        void onCheckedChange(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskHiddenChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }

        @OnCheckedChanged(R.id.hid_task_delete)
        void onCheckedChange1(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskDeleteChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }
    }
    //接口
    public interface OnClickListener {
        void onClick(TodoTask task, int position);
        void onTaskHiddenChanged(TodoTask task,int position,boolean isHidden);
        void onTaskDeleteChanged(TodoTask task, int position, boolean isDone);
    }
}
