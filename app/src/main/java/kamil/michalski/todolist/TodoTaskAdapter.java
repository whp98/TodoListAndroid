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
import butterknife.OnCheckedChanged;
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
        holder.mBlockListeners=true;//blokujemy wysylannia powiadomien o zmianie checkboxa  i kliknnieciu
        holder.mCurrentPosition = task.getId();
        holder.mCurrentTask = task;
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.getDone());
        holder.mBlockListeners=false;//odblokowujemy powiadomienia  zeby poprawnie reagowac na zdarzenia uzytkownika


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
        //true - poniewaz na poczatku kiedy powstaje wiersz i jest rzed przysaniem pierwszego zadaia
        // nie chcemy zeby uruchamialy sie jakiekolwiek funkcje dotyczace zdarzen (np. onClick, on Checked)
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

        //sprawdza czy  checkbox zostal  zaznaczony
        @OnCheckedChanged(R.id.task_done)
        void onCheckedChange(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskDoneChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }
    }


    public interface OnClickListener {
        void onClick(TodoTask task, int position);

        void onTaskDoneChanged(TodoTask task, int position, boolean isDone);
    }
}
