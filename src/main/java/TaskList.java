import java.util.ArrayList;
import tasks.*;

// Represents a collection of tasks and provides methods for managing and displaying them.
public class TaskList {
    protected ArrayList<Task> tasks;

    // Initializes an empty list of tasks
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Initializes the list with an existing collection of tasks
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Returns the entire list of tasks
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    // Replaces the current task list with a new one
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Adds a new task to the end of the list
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    // Retrieves a specific task based on its 1-indexed position
    public Task getTask(int taskNumber) {
        int index = taskNumber - 1;
        return this.tasks.get(index);
    }

    // Returns the total number of tasks currently in the list
    public int getSize() {
        return this.tasks.size();
    }

    // Prints all tasks in the list with their corresponding index numbers
    public void printTasks() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println("  " + (i + 1) + "." + this.tasks.get(i).toString());
        }
    }

    // Marks the task at the specified position as completed
    public void markTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.mark();
    }

    // Marks the task at the specified position as not completed
    public void unmarkTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.unmark();
    }

    // Prints the string representation of a specific task
    public void printTask(int taskNumber) {
        Task task = getTask(taskNumber);
        System.out.println("    " + task.toString());
    }

    // Deletes the task at the specified position from the list
    public void removeTask(int taskNumber) {
        int index = taskNumber - 1;
        this.tasks.remove(index);
    }
}