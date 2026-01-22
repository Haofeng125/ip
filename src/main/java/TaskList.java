import java.util.ArrayList;
import tasks.*;
public class TaskList {
    protected ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task getTask(int taskNumber) {
        int index = taskNumber - 1;
        return this.tasks.get(index);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public void printTasks() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println("  " + (i + 1) + "." + this.tasks.get(i).toString());
        }
    }

    public void markTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.mark();
    }

    public void unmarkTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.unmark();
    }

    public void printTask(int taskNumber) {
        Task task = getTask(taskNumber);
        System.out.println("    " + task.toString());
    }
}