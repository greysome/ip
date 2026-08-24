import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");
    protected LocalDate deadline;

    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = LocalDate.parse(deadline);
        this.type = TaskType.DEADLINE;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", getStatusIcon(), desc, deadline.format(DISPLAY_FORMAT));
    }

    public String getDeadline() {
        return deadline.toString();
    }

    public Deadline(String desc, String deadline, boolean done) {
        this(desc, deadline);
        this.isDone = done;
    }
}
