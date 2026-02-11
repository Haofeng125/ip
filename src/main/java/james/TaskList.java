package james;

import java.util.ArrayList;

import james.task.Task;

/**
 * Represents a collection of tasks and provides methods for managing and displaying them.
 * This class acts as a wrapper around an {@code ArrayList<Task>} to provide task-specific operations.
 */
public class TaskList {
    /** The internal list used to store Task objects. */
    private ArrayList<Task> tasks;

    /**
     * Initializes an empty list of tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initializes the list with an existing collection of tasks.
     *
     * @param tasks An ArrayList of Task objects to populate the list.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return An ArrayList containing all current tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Replaces the current task list with a new one.
     *
     * @param tasks The new ArrayList of Task objects.
     */
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The Task object to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Retrieves a specific task based on its position in the list.
     *
     * @param taskNumber The 1-indexed position of the task in the list.
     * @return The Task object at the specified position.
     * @throws IndexOutOfBoundsException If the taskNumber is invalid.
     */
    public Task getTask(int taskNumber) {
        assert this.tasks != null : "Cannot get task from a null list";
        assert taskNumber > 0 && taskNumber <= this.tasks.size() : "Task index out of bounds";
        int index = taskNumber - 1;
        return this.tasks.get(index);
    }

    /**
     * Returns the total number of tasks currently in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        assert this.tasks != null : "TaskList cannot be null";
        return this.tasks.size();
    }

    /**
     * Instructs the UI to display all tasks in the list.
     *
     * @param ui The UI object responsible for formatting and printing the list.
     */
    public void printTasks(Ui ui) {
        ui.printTasks(this.tasks);
    }

    /**
     * Marks the task at the specified position as completed.
     *
     * @param taskNumber The 1-indexed position of the task.
     */
    public void markTask(int taskNumber) {
        assert this.tasks != null : "Cannot get task from a null list";
        assert taskNumber > 0 && taskNumber <= this.tasks.size() : "Task index out of bounds";
        Task task = getTask(taskNumber);
        task.mark();
    }

    /**
     * Marks the task at the specified position as not completed.
     *
     * @param taskNumber The 1-indexed position of the task.
     */
    public void unmarkTask(int taskNumber) {
        assert this.tasks != null : "Cannot get task from a null list";
        assert taskNumber > 0 && taskNumber <= this.tasks.size() : "Task index out of bounds";
        Task task = getTask(taskNumber);
        task.unmark();
    }

    /**
     * Instructs the UI to print a specific task's details.
     *
     * @param ui The UI object responsible for printing.
     * @param taskNumber The 1-indexed position of the task.
     */
    public void printTask(Ui ui, int taskNumber) {
        assert ui != null : "Ui object cannot be null";
        Task task = getTask(taskNumber);
        ui.printTask(task);
    }

    /**
     * Deletes the task at the specified position from the list.
     *
     * @param taskNumber The 1-indexed position of the task to be removed.
     * @throws IndexOutOfBoundsException If the taskNumber is invalid.
     */
    public void removeTask(int taskNumber) {
        assert this.tasks != null : "Cannot get task from a null list";
        assert taskNumber > 0 && taskNumber <= this.tasks.size() : "Task index out of bounds";
        int index = taskNumber - 1;
        this.tasks.remove(index);
    }
}
