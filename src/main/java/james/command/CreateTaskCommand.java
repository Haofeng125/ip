package james.command;

import java.time.LocalDate;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.task.Deadline;
import james.task.Event;
import james.task.Task;
import james.task.Todo;

public class CreateTaskCommand extends Command {
    private String taskType;
    private String[] descriptions;
    private LocalDate deadlineTime;

    public CreateTaskCommand(String taskType, String[] descriptions, LocalDate deadlineTime) {
        this.taskType = taskType;
        this.descriptions = descriptions;
        this.deadlineTime = deadlineTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public String[] getDescriptions() {
        return descriptions;
    }

    public LocalDate getDeadlineTime() {
        return deadlineTime;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task;

        if (taskType.equals("todo")) {
            task = new Todo(descriptions[0]);
        } else if (taskType.equals("deadline")) {
            task = new Deadline(descriptions[0], this.deadlineTime);
        } else {
            task = new Event(descriptions[0], descriptions[1], descriptions[2]);
        }

        tasks.addTask(task);
        ui.addTask(tasks);
    }
}