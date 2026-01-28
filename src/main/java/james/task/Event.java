package james.task;

// Represents a task that occurs within a specific time frame, including a start and end time.
public class Event extends Task {
    protected String startTime;
    protected String endTime;

    // Initializes an event task with a description, start time, and end time.
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Returns a formatted string displaying the task type, completion status, and time duration.
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to %s)", super.toString(), startTime, endTime);
    }

    @Override
    public String toFileFormat() {
        return String.format("E | %s | %s | %s", super.toFileFormat(), startTime, endTime);
    }
}