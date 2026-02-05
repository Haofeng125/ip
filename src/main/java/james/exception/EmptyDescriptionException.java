package james.exception;

/**
 * Exception thrown when a user provides a command that requires a description,
 * but leaves the description field blank.
 * This class ensures that the user is informed exactly which task type
 * requires more information.
 */
public class EmptyDescriptionException extends JamesException {

    /**
     * Constructs a new EmptyDescriptionException with a message specifying
     * the task type that was missing a description.
     *
     * @param taskType The type of task being created (e.g., "todo", "deadline")
     *     when the error occurred.
     */
    public EmptyDescriptionException(String taskType) {
        super("the description of a " + taskType + " can not be EMPTY, bro!");
    }
}
