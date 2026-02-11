package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.task.Task;

/**
 * Represents a command to remove a task from the task list.
 * This command handles the deletion logic and provides multistep feedback to the user.
 */
public class DeleteTaskCommand extends Command {
    /** The 1-indexed position of the task to be deleted. */
    private final int taskNumber;

    /**
     * Initializes a DeleteTaskCommand with the specified task index.
     *
     * @param taskNumber The position of the task in the list (1-indexed).
     */
    public DeleteTaskCommand(int taskNumber) {
        assert taskNumber >= 0 : "Invalid task number: " + taskNumber;
        this.taskNumber = taskNumber;
    }

    /**
     * {@inheritDoc}
     *
     * @return Always false as deleting a task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * {@inheritDoc}
     * Executes the deletion by first showing the task to be removed,
     * removing it from the task list, and then displaying the updated task count.
     *
     * @param tasks The TaskList from which the task will be removed.
     * @param ui The UI used to display the confirmation messages.
     * @param storage The storage system (not directly used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "Tasks cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        assert taskNumber <= tasks.getSize() : "Invalid task number: " + taskNumber;
        Task taskToDelete = tasks.getTask(this.taskNumber);
        tasks.removeTask(this.taskNumber);
        int remainingSize = tasks.getSize();
        ui.deleteTask(taskToDelete, remainingSize);
    }
}
