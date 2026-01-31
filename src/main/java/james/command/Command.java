package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.exception.JamesException;

/**
 * Represents an executable command in the James application.
 * This abstract class serves as the base for all specific command types,
 * ensuring they implement logic for execution and indicate whether
 * the application should terminate.
 */
public abstract class Command {

    /**
     * Indicates whether this command is a termination command.
     * * @return True if the application should exit after this command, false otherwise.
     */
    public abstract boolean isExit();

    /**
     * Executes the specific logic associated with the command.
     * This may involve modifying the task list, interacting with the UI,
     * or saving data to storage.
     *
     * @param tasks The list of tasks to be operated on.
     * @param ui The user interface for displaying messages.
     * @param storage The storage system for saving and loading data.
     * @throws JamesException If an error occurs during the execution of the command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws JamesException;
}