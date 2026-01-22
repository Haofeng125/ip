package exceptions;

//Throw this when the user types something that can not be identified as any of the existing task types
public class UnknownCommandException extends JamesException {
    public UnknownCommandException(String command) {
        super("I don't know what '" + command + "' means man!");
    }
}