package james.exception;

/**
 * Exception thrown when the application encounters an I/O error while
 * attempting to save task data to the local storage file.
 * This class translates technical file system errors into a message
 * consistent with James's personality.
 */
public class CanNotWriteToFileException extends JamesException {

    /**
     * Constructs a new CanNotWriteToFileException with a predefined error message.
     *
     * @param message The original technical error message from the I/O failure
     *     (not currently displayed to the user).
     */
    public CanNotWriteToFileException(String message) {
        super("Opps man! I can not write to your file 'task.txt'.");
    }
}
