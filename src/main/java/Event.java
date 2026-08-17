public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
        this.type = TaskType.EVENT;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)", getStatusIcon(), desc, from, to);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
