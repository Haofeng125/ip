package james.exception;

public class CanNotWriteToFileException extends JamesException {
    public CanNotWriteToFileException(String message) {
        super("Opps man! I can not write to your file 'task.txt'.");
    }
}