public class Deadline extends Task {
    protected String deadline;

    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
        this.type = TaskType.DEADLINE;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", getStatusIcon(), desc, deadline);
    }

    public String getDeadline() {
        return deadline;
    }

    public Deadline(String desc, String deadline, boolean done) {
        this(desc, deadline);
        this.isDone = done;
    }
}
