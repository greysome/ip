package puke;

public class Task {
    protected String desc;
    protected boolean isDone;
    protected TaskType type;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
        this.type = TaskType.TODO;
    }

    public Task(String desc, boolean done) {
        this(desc);
        this.isDone = done;
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", getStatusIcon(), desc);
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public String getDesc() {
        return desc;
    }

    /** Returns whether this task description contains the given keyword. */
    public boolean matchesKeyword(String keyword) {
        return desc.toLowerCase().contains(keyword.toLowerCase());
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public TaskType getType() {
        return type;
    }
}
