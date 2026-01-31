package james.command;

import james.Ui;
import james.Storage;
import james.TaskList;

/**
 * Represents a command to search for a task including specific contents in the task list.
 * This command handles the searching logic and provide the according tasks to the user.
 */
public class FindTaskCommand extends Command {
    // The key word used in searching
    private String keyWord;

    /**
     * Initializes a FindCommand with the specific key word given.
     *
     * @param keyWord The specific key word that we need to look up in the task description.
     */
    public FindTaskCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * {@inheritDoc}
     * * @return Always false as searching through tasks does not exit the application.
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
        ui.findTask(tasks.getTasks(), keyWord);
    }
}
