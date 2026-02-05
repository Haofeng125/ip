package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.exception.JamesException;

/**
 * Represents a command to safely terminate the James chatbot application.
 * This command handles the final saving of data to the hard disk and
 * displays the departure message to the user.
 */
public class TerminateProgramCommand extends Command {

    /**
     * {@inheritDoc}
     *
     * @return Always true, signaling the main application loop to terminate.
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * {@inheritDoc}
     * Saves the current state of the task list to storage and displays
     * the goodbye message.
     *
     * @param tasks The TaskList to be saved before exiting.
     * @param ui The UI used to display the "bye" message.
     * @param storage The storage system used to persist the task data.
     * @throws JamesException If an error occurs during the file saving process.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JamesException {
        storage.saveTasks(tasks);
        ui.bye();
    }
}
