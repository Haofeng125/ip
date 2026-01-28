package james.exception;

//This is the parent exception class that all specific errors inherit from
public class JamesException extends Exception {
    public JamesException(String message) {
        super(message);
    }
}