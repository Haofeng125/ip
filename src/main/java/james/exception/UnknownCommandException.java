package james.exception;

public class UnknownCommandException extends JamesException {
    public UnknownCommandException(String command) {
        super("I don't know what '" + command + "' means man!");
    }
}