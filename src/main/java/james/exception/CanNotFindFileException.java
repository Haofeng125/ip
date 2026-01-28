package james.exception;

public class CanNotFindFileException extends JamesException {
    public CanNotFindFileException(String message) {
        super("I can not find such a file man!");
    }
}
