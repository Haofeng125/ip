package james.exception;

/**
 * Exception thrown when the user enters a command that the James chatbot
 * does not recognize.
 * This class captures the invalid input to provide a personalized error
 * message back to the user.
 */
public class UnknownCommandException extends JamesException {

    /**
     * Constructs a new UnknownCommandException with a message identifying
     * the unrecognized command.
     *
     * @param command The raw string input that could not be identified as a valid command.
     */
    public UnknownCommandException(String command) {
        super("I don't know what '" + command + "' means man!");
    }
}
