package james.command;

import james.*;
import james.task.*;
import java.time.LocalDate;

public class CreateTaskCommand extends Command {
    private String taskType;
    private String[] descriptions;
    private LocalDate deadlineTime;

    public CreateTaskCommand(String taskType, String[] descriptions, LocalDate deadlineTime) {
        this.taskType = taskType;
        this.descriptions = descriptions;
        this.deadlineTime = deadlineTime;
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
