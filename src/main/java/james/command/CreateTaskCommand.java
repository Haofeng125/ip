package james.command;

import java.time.LocalDate;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.task.Deadline;
import james.task.Event;
import james.task.Task;
import james.task.Todo;

/**
 * Handles the creation and addition of various task types to the task list.
 * This command can instantiate Todo, Deadline, or Event objects based on
 * the provided task type and descriptions.
 */
public class CreateTaskCommand extends Command {
    /** The type of task to create (e.g., "todo", "deadline", "event"). */
    private String taskType;
    /** An array containing description parts (e.g., task name, start time, end time). */
    private String[] descriptions;
    /** The due date for deadline tasks; null for other task types. */
    private LocalDate deadlineTime;

    /**
     * Initializes a CreateTaskCommand with the necessary data to build a task.
     *
     * @param taskType The category of the task.
     * @param descriptions The array of strings containing task details.
     * @param deadlineTime The LocalDate for deadlines, or null if not applicable.
     */
    public CreateTaskCommand(String taskType, String[] descriptions, LocalDate deadlineTime) {
        this.taskType = taskType;
        this.descriptions = descriptions;
        this.deadlineTime = deadlineTime;
    }

    /**
     * Returns the type of task this command is responsible for creating.
     *
     * @return The task type string.
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Returns the description parts used to create the task.
     *
     * @return An array of description strings.
     */
    public String[] getDescriptions() {
        return descriptions;
    }

    /**
     * Returns the deadline time associated with this command.
     *
     * @return The LocalDate object, or null.
     */
    public LocalDate getDeadlineTime() {
        return deadlineTime;
    }

    /**
     * {@inheritDoc}
     *
     * @return Always false as adding a task does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * {@inheritDoc}
     * Logic to instantiate the specific Task subclass, add it to the list,
     * and trigger the UI confirmation message.
     *
     * @param tasks The TaskList where the new task will be added.
     * @param ui The UI used to confirm the addition to the user.
     * @param storage The storage system (not directly used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "Tasks cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        Task task;

        assert descriptions != null : "Descriptions cannot be null";
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
