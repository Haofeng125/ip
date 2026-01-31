package james.task;

/**
 * Represents a general task in the James application.
 * A task consists of a description and a completion status. This class serves
 * as a base for more specific types of tasks.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Initializes a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon representing whether the task is completed.
     * "X" indicates completed, and a space " " indicates not completed.
     *
     * @return A string icon representing completion status.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Sets the task's completion status to true.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Sets the task's completion status to false.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return A formatted string suitable for display in the UI.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    /**
     * Returns a string representation of the task formatted for file storage.
     *
     * @return A pipe-separated string containing completion status and description.
     */
    public String toFileFormat() {
        return String.format("%s | %s", (this.isDone ? "1" : "0"), this.description);
    }
}