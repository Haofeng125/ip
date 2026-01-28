package james.command;

import james.*;
import james.task.*;

public class DeleteTaskCommand extends Command {
    private int taskNumber;

    public DeleteTaskCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.deleteTask1(tasks, this.taskNumber);
        tasks.removeTask(taskNumber);
        ui.deleteTask2(tasks);
    }
}