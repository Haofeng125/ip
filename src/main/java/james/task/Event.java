package james.task;

/**
 * Represents a task that occurs within a specific time frame.
 * An Event includes a start time and an end time in addition to the
 * basic task description and status.
 */
public class Event extends Task {
    private String startTime;
    private String endTime;

    /**
     * Initializes a new Event task with a description, start time, and end time.
     *
     * @param description The textual description of the event.
     * @param startTime The string representation of the start time.
     * @param endTime The string representation of the end time.
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * {@inheritDoc}
     * Prefixes the representation with "[E]" and appends the event duration.
     *
     * @return A formatted string displaying task type, status, and time range.
     */
    @Override
    public String toString() {
        assert startTime != null : "Start time cannot be null";
        assert endTime != null : "End time cannot be null";
        return String.format("[E]%s (from: %s to %s)", super.toString(), this.startTime, this.endTime);
    }

    /**
     * {@inheritDoc}
     * Identifies the task as an "E" and appends the start and end times.
     *
     * @return A pipe-separated string formatted for storage.
     */
    @Override
    public String toFileFormat() {
        assert startTime != null : "Start time cannot be null";
        assert endTime != null : "End time cannot be null";
        return String.format("E | %s | %s | %s", super.toFileFormat(), this.startTime, this.endTime);
    }
}
