package tasks;

// Represents a specific type of task that includes a description and a completion deadline.
public class Deadline extends Task {
    protected String deadline;

    // Initializes a deadline task with a description and a due date
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    // Returns a formatted string displaying the task type, status, and deadline
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline);
    }
}