package james.exception;

/**
 * Represents the base exception class for the James application.
 * This class serves as the parent for all specific exceptions thrown
 * by the James chatbot, allowing for centralized error handling.
 */
public class JamesException extends Exception {

    /**
     * Constructs a new JamesException with the specified detail message.
     *
     * @param message The specific error message describing the failure.
     */
    public JamesException(String message) {
        super(message);
    }
}