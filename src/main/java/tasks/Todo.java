package tasks;

// Represents a simple task without any specific time constraints. public class Todo extends Task
public class Todo extends Task {

    // Initializes a new Todo task with the specified description.
    public Todo(String description) {
        super(description);
    }

    // Returns a formatted string displaying the task type and completion status.
    @Override
    public String toString(){
        return String.format("[T]%s", super.toString());
    }

    @Override
    public String toFileFormat(){
        return String.format("T | %s", super.toFileFormat());
    }
}