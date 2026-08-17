public class Task {
    protected String desc;
    protected boolean isDone;
    protected TaskType type;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
        this.type = TaskType.TODO;
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
