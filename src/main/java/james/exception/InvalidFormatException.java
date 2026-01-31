package james.exception;

/**
 * Exception thrown when a user's command does not follow the expected syntax.
 * Provides specific factory methods to generate error messages for deadlines,
 * events, and date formatting.
 */
public class InvalidFormatException extends JamesException {

    /**
     * Private constructor to enforce the use of static factory methods.
     *
     * @param message The detailed error message to be displayed to the user.
     */
    private InvalidFormatException(String message) {
        super(message);
    }

    /**
     * Returns an instance for cases where a date string cannot be parsed.
     * Expected format is ISO-8601 (yyyy-MM-dd).
     *
     * @return An InvalidFormatException with date format instructions.
     */
    public static InvalidFormatException forLocalDate() {
        return new InvalidFormatException("Hey man, you need to input your date in the format of 'yyyy-MM-dd'.");
    }

    /**
     * Returns an instance for cases where a deadline command is missing the '/by' delimiter.
     *
     * @return An InvalidFormatException with deadline syntax instructions.
     */
    public static InvalidFormatException forDeadline() {
        return new InvalidFormatException("Bro, deadlines need a '/by' time! Use: deadline [desc] /by [time]");
    }

    /**
     * Returns an instance for cases where an event command is missing '/from' or '/to' delimiters.
     *
     * @return An InvalidFormatException with event syntax instructions.
     */
    public static InvalidFormatException forEvent() {
        return new InvalidFormatException("Nah man, events need '/from' and '/to'! Use: event [desc] /from [s] /to [e]");
    }
}