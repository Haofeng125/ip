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
 */
public class CreateTaskCommand extends Command {

    /**
     * Use Enumerations: Defines supported task categories.
     */
    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    private final TaskType taskType;
    private final String[] descriptions;
    private final LocalDate deadlineTime;

    /**
     * Initializes a CreateTaskCommand.
     *
     * @param taskTypeString String to be converted into a TaskType enum.
     * @param descriptions   Array of details for the task.
     * @param deadlineTime   Date for deadlines, or null.
     */
    public CreateTaskCommand(String taskTypeString, String[] descriptions, LocalDate deadlineTime) {
        this.taskType = TaskType.valueOf(taskTypeString.toUpperCase());
        this.descriptions = descriptions;
        this.deadlineTime = deadlineTime;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task newTask = createSpecificTask();

        tasks.addTask(newTask);
        ui.addTask(tasks);
    }

    /**
     * SLAP: Low-level instantiation logic moved to a helper method.
     *
     * @return The specific Task object (Todo, Deadline, or Event).
     */
    private Task createSpecificTask() {
        switch (this.taskType) {
        case TODO:
            return new Todo(descriptions[0]);
        case DEADLINE:
            return new Deadline(descriptions[0], this.deadlineTime);
        case EVENT:
            return new Event(descriptions[0], descriptions[1], descriptions[2]);
        default:
            throw new IllegalStateException("Unexpected task type: " + taskType);
        }
    }
}
