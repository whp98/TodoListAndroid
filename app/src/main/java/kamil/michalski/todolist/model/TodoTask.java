package kamil.michalski.todolist.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
//任务实例类
@DatabaseTable(tableName = "todo_task")
public class TodoTask {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField
    private String note;
    @DatabaseField(canBeNull = false)
    private boolean done;
    @DatabaseField(canBeNull = false)
    private boolean hiden;
    @DatabaseField(canBeNull = false)
    private Date dateCreated;
    @DatabaseField(canBeNull = false)
    private boolean reminder;
    @DatabaseField
    private Date reminderDate;

    public void setHiden(boolean hiden){this.hiden = hiden;};

    public boolean isHiden() {
        return hiden;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
