package james.task;

import java.time.LocalDate;

/**
 * Represents a task with a specific deadline.
 * A Deadline task includes a date by which the task must be completed,
 * in addition to the standard task description and status.
 */
public class Deadline extends Task {
    /** The date by which the task is due. */
    private LocalDate deadlineTime;

    /**
     * Initializes a new Deadline task with a description and a due date.
     *
     * @param description The textual description of the task.
     * @param deadlineTime The LocalDate representing the deadline.
     */
    public Deadline(String description, LocalDate deadlineTime) {
        super(description);
        this.deadlineTime = deadlineTime;
    }

    /**
     * {@inheritDoc}
     * Prefixes the representation with "[D]" and appends the deadline
     * formatted as "Month Day Year" (e.g., Jan 31 2026).
     *
     * @return A formatted string displaying task type, status, and due date.
     */
    @Override
    public String toString() {
        String month = this.deadlineTime.getMonth().name();
        int year = this.deadlineTime.getYear();
        int day = this.deadlineTime.getDayOfMonth();
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
        return String.format("[D]%s (by: %s %s %d)", super.toString(), month, day, year);
    }

    /**
     * {@inheritDoc}
     * Identifies the task as "D" and appends the deadline date in YYYY-MM-DD format.
     *
     * @return A pipe-separated string formatted for storage.
     */
    @Override
    public String toFileFormat() {
        int month = this.deadlineTime.getMonthValue();
        int year = this.deadlineTime.getYear();
        int day = this.deadlineTime.getDayOfMonth();
        return String.format("D | %s | %04d-%02d-%02d", super.toFileFormat(), year, month, day);
    }
}