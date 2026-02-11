package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;

/**
 * Represents a command to mark a specific task as not completed yet.
 * This command updates the status of the task in the task list and
 * triggers a corresponding confirmation message via the UI.
 */
public class UnmarkCommand extends Command {
    /** The 1-indexed position of the task to be unmarked. */
    private final int taskNumber;

    /**
     * Initializes an UnmarkCommand with the specified task index.
     *
     * @param taskNumber The position of the task in the list (1-indexed).
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * {@inheritDoc}
     *
     * @return Always false as unmarking a task does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * {@inheritDoc}
     * Updates the status of the specified task to "not done" and displays
     * the updated task details to the user.
     *
     * @param tasks The TaskList containing the task to be updated.
     * @param ui The UI used to display the unmarking confirmation.
     * @param storage The storage system (not used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.unmarkTask(this.taskNumber);
        ui.unmarkTask(tasks, this.taskNumber);
    }
}
