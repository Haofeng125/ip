package james.task;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Represents a specific type of task that includes a description and a completion deadline.
public class Deadline extends Task {
    protected LocalDate deadlineTime;

    // Initializes a deadline task with a description and a due date
    public Deadline(String description, LocalDate deadlineTime) {
        super(description);
        this.deadlineTime = deadlineTime;
    }

    // Returns a formatted string displaying the task type, status, and deadline
    @Override
    public String toString() {
        String month = this.deadlineTime.getMonth().name();
        int year = this.deadlineTime.getYear();
        int day = this.deadlineTime.getDayOfMonth();
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
        return String.format("[D]%s (by: %s %s %d)", super.toString(), month, day, year);
    }

    @Override
    public String toFileFormat() {
        int month = this.deadlineTime.getMonthValue();
        int year = this.deadlineTime.getYear();
        int day = this.deadlineTime.getDayOfMonth();
        return String.format("D | %s | %04d-%02d-%02d", super.toFileFormat(), year, month, day);
    }
}