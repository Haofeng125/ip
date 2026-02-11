package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;

/**
 * Represents a command to mark a specific task as completed.
 * This command updates the status of the task in the task list and
 * triggers a confirmation message to the user.
 */
public class MarkCommand extends Command {
    /** The 1-indexed position of the task to be marked as done. */
    private final int taskNumber;

    /**
     * Initializes a MarkCommand with the specified task index.
     *
     * @param taskNumber The position of the task in the list (1-indexed).
     */
    public MarkCommand(int taskNumber) {
        assert taskNumber >= 0 : "Invalid task number: " + taskNumber;
        this.taskNumber = taskNumber;
    }

    /**
     * {@inheritDoc}
     *
     * @return Always false as marking a task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * {@inheritDoc}
     * Updates the status of the specified task to "done" and displays
     * the updated task details via the UI.
     *
     * @param tasks The TaskList containing the task to be marked.
     * @param ui The UI used to display the completion confirmation.
     * @param storage The storage system (not directly used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "Tasks cannot be null";
        assert taskNumber <= tasks.getSize() : "Invalid task number: " + taskNumber;
        assert storage != null : "Storage cannot be null";
        assert ui != null : "Ui cannot be null";
        tasks.markTask(this.taskNumber);
        ui.markTask(tasks, this.taskNumber);
    }
}
