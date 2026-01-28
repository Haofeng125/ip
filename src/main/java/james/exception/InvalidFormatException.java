package james.exception;

//Throw this when the user's input has an invalid format
public class InvalidFormatException extends JamesException {
    private InvalidFormatException(String message) {
        super(message);
    }

    public static InvalidFormatException forLocalDate() {
        return new InvalidFormatException("Hey man, you need to input your date in the format of 'yyyy-MM-dd'.");
    }

    public static InvalidFormatException forDeadline() {
        return new InvalidFormatException("Bro, deadlines need a '/by' time! Use: deadline [desc] /by [time]");
    }

    public static InvalidFormatException forEvent() {
        return new InvalidFormatException("Nah man, events need '/from' and '/to'! Use: event [desc] /from [s] /to [e]");
    }
}