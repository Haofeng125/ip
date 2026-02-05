package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;

/**
 * Represents a command to display all tasks currently in the task list.
 * This command triggers the UI to format and print the entire collection of tasks.
 */
public class ListCommand extends Command {

    /**
     * {@inheritDoc}
     *
     * @return Always false as listing tasks does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * {@inheritDoc}
     * Triggers the UI to display the current list of tasks to the user.
     *
     * @param tasks The TaskList to be displayed.
     * @param ui The UI used to format and print the list.
     * @param storage The storage system (not used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printList(tasks);
    }
}
