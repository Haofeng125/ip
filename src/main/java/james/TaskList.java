package james;

import java.util.ArrayList;

import james.task.Task;

public class TaskList {
    private ArrayList<Task> tasks;

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

    public void printTasks(Ui ui) {
        ui.printTasks(this.tasks);
    }

    public void markTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.mark();
    }

    public void unmarkTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.unmark();
    }

    public void printTask(Ui ui, int taskNumber) {
        Task task = getTask(taskNumber);
        ui.printTask(task);
    }

    public void removeTask(int taskNumber) {
        int index = taskNumber - 1;
        this.tasks.remove(index);
    }
}