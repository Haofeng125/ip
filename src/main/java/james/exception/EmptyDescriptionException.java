package james.exception;

//Throw this when a user provides a command like "todo" but leaves the description black
public class  EmptyDescriptionException extends JamesException {
    public EmptyDescriptionException(String taskType) {
        super("the description of a " + taskType + " can not be EMPTY, bro!");
    }
}