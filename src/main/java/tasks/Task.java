package tasks;

// Represents a general task containing a description and a completion status.
public class Task {
    protected String description;
    protected boolean isDone;

    // Initializes a new task with a description and sets its completion status to false.
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    // Returns a string icon representing the task's completion status.
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    // Sets the task's completion status to true.
    public void mark() {
        this.isDone = true;
    }

    // Sets the task's completion status to false.
    public void unmark() {
        this.isDone = false;
    }

    // Returns a formatted string showing the status icon and the task description.
    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }
}