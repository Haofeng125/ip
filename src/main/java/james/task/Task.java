package james.task;

import java.util.ArrayList;

/**
 * Represents a general task in the James application.
 * A task consists of a description and a completion status. This class serves
 * as a base for more specific types of tasks.
 */
public class Task {
    private final String description;
    private boolean isDone;
    private ArrayList<String> tags;

    /**
     * Initializes a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     * @param tags The tags of the task.
     */
    public Task(String description, ArrayList<String> tags) {
        assert description != null : "Description cannot be null";
        this.description = description;
        this.isDone = false;
        this.tags = tags;
    }

    /**
     * Initializes a new task with the specified description.
     * The task is initially marked as not done.
     * The tags of the task is initially an emp
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null : "Description cannot be null";
        this.description = description;
        this.isDone = false;
        this.tags = new ArrayList<>();
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
     * Add a tag to the task.
     *
     * @param tag The tag we want to attach to the task.
     */
    public void addTag(String tag) {
        this.tags.add(tag);
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
     * Returns a string representation of all the tags of the task.
     *
     * @return The String information of all tags.
     */
    public String tagsToString() {
        StringBuilder builder = new StringBuilder();
        for (String tag : this.tags) {
            String hashedTag = "#" + tag;
            builder.append(hashedTag);
        }
        return builder.toString();
    }

    /**
     * Returns a string representation of all the tags of the task that can be stored in the file.
     *
     * @return The String information of all tags in file format.
     */
    public String tagsToFile() {
        StringBuilder builder = new StringBuilder();
        for (String tag : this.tags) {
            String modifiedTag = " /#" + tag;
            builder.append(modifiedTag);
        }
        return builder.toString();
    }

    /**
     * Returns a string of "1" or "0" to specify the status of the task.
     *
     * @return "1" for done tasks and "0" for undone tasks.
     */
    public String isDoneToFile() {
        return this.isDone ? "1" : "0";
    }

    /**
     * Returns a string representation of the task formatted for file storage.
     *
     * @return A pipe-separated string containing completion status and description.
     */
    public String toFileFormat() {
        return String.format("%s | %s | %s", this.tagsToFile(), this.isDoneToFile(), this.description);
    }
}
