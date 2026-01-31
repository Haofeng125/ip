package james.exception;

/**
 * Exception thrown when a command requires a task number, but the input
 * is missing, not a valid integer, or outside the range of the task list.
 */
public class InvalidNumberException extends JamesException {

    /**
     * Private constructor to prevent direct instantiation.
     * Use the static factory methods to create specific instances.
     *
     * @param message The detailed error message.
     */
    private InvalidNumberException(String message) {
        super(message);
    }

    /**
     * Returns an instance for cases where the input is not a parseable integer.
     *
     * @return An InvalidNumberException with a "not a number" message.
     */
    public static InvalidNumberException forNotANumber() {
        return new InvalidNumberException("Chill, that's not a number! Use something like 'mark 1'.");
    }

    /**
     * Returns an instance for cases where the task number does not exist in the list.
     *
     * @return An InvalidNumberException with an "out of range" message.
     */
    public static InvalidNumberException forNumberOutOfRange() {
        return new InvalidNumberException("Man, the number you entered is out of the range of your task list!");
    }

    /**
     * Returns an instance for cases where the command was given without a number.
     *
     * @param command The specific command word (e.g., "mark", "delete") to include in the message.
     * @return An InvalidNumberException with a "missing number" message.
     */
    public static InvalidNumberException forMissingNumber(String command) {
        return new InvalidNumberException("You need to at least tell me which number to " + command + " hommie!");
    }
}