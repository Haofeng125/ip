package exceptions;

public class InvalidNumberException extends JamesException {
    private InvalidNumberException(String message) {
        super(message);
    }

    public static InvalidNumberException forNotANumber() {
        return new InvalidNumberException("Chill, that's not a number! Use something like 'mark 1'.");
    }

    public static InvalidNumberException forNumberOutOfRange() {
        return new InvalidNumberException("Man, the number you entered is out of the range of your task list!");
    }

    public static InvalidNumberException forMissingNumber(String command) {
        return new InvalidNumberException("You need to at least tell me which number to " + command + " hommie!");
    }
}
