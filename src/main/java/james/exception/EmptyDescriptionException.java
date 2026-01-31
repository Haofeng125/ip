package james.exception;

public class EmptyDescriptionException extends JamesException {
    public EmptyDescriptionException(String taskType) {
        super("the description of a " + taskType + " can not be EMPTY, bro!");
    }
}