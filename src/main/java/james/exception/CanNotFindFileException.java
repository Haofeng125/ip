package james.exception;

/**
 * Exception thrown when the application is unable to locate the specified
 * data storage file on the hard disk.
 * This typically occurs during the initialization phase if the data folder
 * or file does not yet exist.
 */
public class CanNotFindFileException extends JamesException {

    /**
     * Constructs a new CanNotFindFileException with a friendly warning message.
     *
     * @param message The original detail message explaining the file's absence
     *     (not displayed to the user).
     */
    public CanNotFindFileException(String message) {
        super("I can not find such a file man!");
    }
}
