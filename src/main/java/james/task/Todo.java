package james.task;

import java.util.ArrayList;

/**
 * Represents a "Todo" task in the James application.
 * A Todo is a basic task that contains only a description and does not have
 * any specific date or time constraints.
 */
public class Todo extends Task {

    /**
     * Initializes a new Todo task with the specified description.
     *
     * @param description The textual description of the todo item.
     * @param tags The tags for the todo item.
     */
    public Todo(String description, ArrayList<String> tags) {
        super(description, tags);
    }

    /**
     * Initializes a new Todo task with the specified description.
     *
     * @param description The textual description of the todo item.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * {@inheritDoc}
     * Prefixes the task representation with a "[T]" to indicate its type.
     *
     * @return A formatted string representation of the Todo task.
     */
    @Override
    public String toString() {
        return String.format("[T]%s %s", super.toString(), super.tagsToString());
    }

    /**
     * {@inheritDoc}
     * Prefixes the file storage format with "T" to identify it as a Todo.
     *
     * @return A string formatted for storage in the data file.
     */
    @Override
    public String toFileFormat() {
        return String.format("T | %s", super.toFileFormat());
    }
}
