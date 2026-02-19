package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.exception.JamesException;
import james.task.Task;

/**
 * Represents a command to add tags to specific tasks.
 */
public class TagTaskCommand extends Command {
    private final String tag;
    private final int taskNumber;

    /**
     * Initialized a TagTaskCommand with the specific tag contents.
     *
     * @param tag The contents user want to tag to the task.
     * @param taskNumber The taskNumber to specify the task.
     */
    public TagTaskCommand(String tag, int taskNumber) {
        assert tag != null : "tag cannot be null";
        assert taskNumber > 0 : "taskNumber cannot be negative";
        this.tag = tag;
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
     * Add a tag to the specified task
     *
     * @param tasks The TaskList containing the task to be marked.
     * @param ui The UI used to display the completion confirmation.
     * @param storage The storage system (not directly used by this command).
     * @throws JamesException If an error occurs during the file saving process.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JamesException {
        Task task = tasks.getTask(this.taskNumber);
        task.addTag(this.tag);
        ui.tagTask(tasks, this.taskNumber);
        storage.saveTasks(tasks);
    }
}
