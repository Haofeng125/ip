package james;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import james.command.Command;
import james.command.CreateTaskCommand;
import james.command.DeleteTaskCommand;
import james.command.FindTaskCommand;
import james.command.ListCommand;
import james.command.MarkCommand;
import james.command.TagTaskCommand;
import james.command.TerminateProgramCommand;
import james.command.UnmarkCommand;
import james.exception.EmptyDescriptionException;
import james.exception.InvalidFormatException;
import james.exception.InvalidNumberException;
import james.exception.JamesException;
import james.exception.UnknownCommandException;
import james.task.Deadline;
import james.task.Event;
import james.task.Task;
import james.task.Todo;

/**
 * Deals with making sense of the user command and translating it into program actions.
 * Provides methods to parse raw user input into Command objects and strings from
 * storage files into Task objects.
 */
public class Parser {

    /**
     * [W5.4f] Use Enumerations: Defines the valid command words to prevent "magic strings".
     * Represents the specific command types supported by the application.
     */
    private enum CommandType {
        BYE, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, LIST, FIND, TAG
    }

    private static final String SEP_BY = " /by ";
    private static final String SEP_FROM = " /from ";
    private static final String SEP_TO = " /to ";
    private static final String SEP_STORAGE = " \\| ";
    private static final String SEP_TAG = " /#";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String STATUS_DONE = "1";

    /**
     * Parses a raw input string into an executable Command object.
     * Delegates specific command logic based on the identified command type.
     *
     * @param fullCommand The full line of text entered by the user.
     * @param tasks The current list of tasks, used for validation in some commands.
     * @return A Command object representing the user's intent.
     * @throws JamesException If the command is unknown, arguments are invalid, or formats are incorrect.
     */
    public static Command parseCommand(String fullCommand, TaskList tasks) throws JamesException {
        String[] commandParts = splitCommand(fullCommand);
        String commandWord = commandParts[0];
        String arguments = commandParts.length > 1 ? commandParts[1] : "";

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException(fullCommand);
        }

        switch (commandType) {
        case BYE:
            return new TerminateProgramCommand();
        case MARK:
        case UNMARK:
        case DELETE:
            return handleTaskModification(commandType, arguments, tasks);
        case TODO:
            return handleTodo(arguments);
        case DEADLINE:
            return handleDeadline(arguments);
        case EVENT:
            return handleEvent(arguments);
        case LIST:
            return new ListCommand();
        case FIND:
            return new FindTaskCommand(arguments);
        case TAG:
            return handleTagging(arguments, tasks);
        default:
            throw new UnknownCommandException(fullCommand);
        }
    }

    /**
     * Splits the full command string into the command word and its arguments.
     *
     * @param fullCommand The raw input string.
     * @return A string array where index 0 is the command and index 1 is arguments (if any).
     */
    private static String[] splitCommand(String fullCommand) {
        return fullCommand.split(" ", 2);
    }

    /**
     * Handles commands that modify existing tasks (MARK, UNMARK, DELETE).
     * Uses CommandType enum for logic instead of raw strings.
     *
     * @param type The specific modification command type.
     * @param arguments The string containing the task index.
     * @param tasks The task list used for index validation.
     * @return The specific modification Command.
     * @throws JamesException If the index is missing, invalid, or out of bounds.
     */
    private static Command handleTaskModification(CommandType type, String arguments, TaskList tasks)
            throws JamesException {
        if (arguments.isEmpty()) {
            throw InvalidNumberException.forMissingNumber(type.name().toLowerCase());
        }

        int taskNumber = parseTaskNumber(arguments);
        validateTaskNumberInRange(taskNumber, tasks);

        switch (type) {
        case MARK:
            return new MarkCommand(taskNumber);
        case UNMARK:
            return new UnmarkCommand(taskNumber);
        case DELETE:
            return new DeleteTaskCommand(taskNumber);
        default:
            throw new UnknownCommandException(type.name());
        }
    }

    /**
     * Handles commands that tags the task.
     *
     * @param arguments The string containing the task index and the tag content.
     * @return The target TagTaskCommand.
     * @throws JamesException If the index is missing, invalid, or out of bounds.
     */
    private static Command handleTagging(String arguments, TaskList tasks) throws JamesException {
        final String commandType = "tag";
        if (arguments.isEmpty()) {
            throw new EmptyDescriptionException(commandType);
        }
        String[] tagParts = arguments.split(" ", 2);
        if (tagParts.length != 2) {
            throw new EmptyDescriptionException(commandType);
        }

        int taskNumber = parseTaskNumber(tagParts[0]);
        validateTaskNumberInRange(taskNumber, tasks);

        String tag = tagParts[1];
        return new TagTaskCommand(tag, taskNumber);
    }

    /**
     * Parses the task number string into an integer.
     *
     * @param arguments The string containing the number.
     * @return The parsed integer.
     * @throws InvalidNumberException If the string is not a valid number.
     */
    private static int parseTaskNumber(String arguments) throws InvalidNumberException {
        try {
            return Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw InvalidNumberException.forNotANumber();
        }
    }

    /**
     * Validates that the task number exists within the current task list.
     *
     * @param taskNumber The parsed task number.
     * @param tasks The task list to check against.
     * @throws InvalidNumberException If the number is out of bounds.
     */
    private static void validateTaskNumberInRange(int taskNumber, TaskList tasks) throws InvalidNumberException {
        if (taskNumber <= 0 || taskNumber > tasks.getSize()) {
            throw InvalidNumberException.forNumberOutOfRange();
        }
    }

    /**
     * Creates a Todo command from the provided arguments.
     *
     * @param arguments The description of the todo.
     * @return A CreateTaskCommand configured for a Todo.
     * @throws EmptyDescriptionException If the description is empty.
     */
    private static Command handleTodo(String arguments) throws EmptyDescriptionException {
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException(CommandType.TODO.name().toLowerCase());
        }
        return new CreateTaskCommand(CommandType.TODO.name().toLowerCase(), new String[]{arguments.trim()}, null);
    }

    /**
     * Creates a Deadline command by parsing the description and date.
     *
     * @param arguments The raw arguments containing description and deadline date.
     * @return A CreateTaskCommand configured for a Deadline.
     * @throws InvalidFormatException If the format or date is invalid.
     */
    private static Command handleDeadline(String arguments) throws InvalidFormatException {
        if (!arguments.contains(SEP_BY)) {
            throw InvalidFormatException.forDeadline();
        }

        String[] parts = arguments.split(SEP_BY, 2);
        if (parts.length < 2) {
            throw InvalidFormatException.forDeadline();
        }

        LocalDate deadlineTime = parseDate(parts[1]);
        return new CreateTaskCommand(CommandType.DEADLINE.name().toLowerCase(), parts, deadlineTime);
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDate.
     * @throws InvalidFormatException If the date format is incorrect.
     */
    private static LocalDate parseDate(String dateString) throws InvalidFormatException {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (DateTimeParseException e) {
            throw InvalidFormatException.forLocalDate();
        }
    }

    /**
     * Creates an Event command by parsing the description, start time, and end time.
     *
     * @param arguments The raw arguments containing description and time range.
     * @return A CreateTaskCommand configured for an Event.
     * @throws InvalidFormatException If the format is invalid.
     */
    private static Command handleEvent(String arguments) throws InvalidFormatException {
        if (!arguments.contains(SEP_FROM) || !arguments.contains(SEP_TO)) {
            throw InvalidFormatException.forEvent();
        }

        String[] splitFrom = arguments.split(SEP_FROM);
        String description = splitFrom[0];
        String[] splitTo = splitFrom[1].split(SEP_TO);

        if (splitTo.length < 2) {
            throw InvalidFormatException.forEvent();
        }

        String startTime = splitTo[0];
        String endTime = splitTo[1];

        return new CreateTaskCommand(CommandType.EVENT.name().toLowerCase(),
                new String[]{description, startTime, endTime}, null);
    }

    /**
     * Parses a single line from the data storage file into a Task object.
     *
     * @param line The pipe-separated string representing a task in the storage file.
     * @return The reconstructed Task object, or null if the task type is unknown.
     */
    public static Task parseLine(String line) {
        // Assertion: Ensure storage line input is not null
        assert line != null : "Storage line cannot be null";
        String[] parts = line.split(SEP_STORAGE);
        if (parts.length < 4) {
            return null;
        }

        String type = parts[0].toUpperCase();
        ArrayList<String> tags = loadTags(parts[1]);
        boolean isDone = parts[2].equals(STATUS_DONE);
        String description = parts[3];
        Task task = createTaskFromStorage(type, tags, parts, description);

        if (task != null && isDone) {
            task.mark();
        }
        return task;
    }

    /**
     * Parse a line of file format String for tags to tags themselves.
     *
     * @param tagInfo The file format String for tags.
     * @return The tags.
     */
    public static ArrayList<String> loadTags(String tagInfo) {
        assert tagInfo != null : "Storage tags cannot be null";
        String[] parts = tagInfo.split(" /#");
        if (parts.length < 2) {
            return new ArrayList<>();
        }
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(parts));
        tags.remove(0);
        return tags;
    }

    /**
     * Creates a specific Task object based on the type identifier from storage.
     *
     * @param type The task type identifier (T, D, E).
     * @param parts The split line array containing task details.
     * @param description The task description.
     * @return The specific Task object, or null if the type is unknown.
     */
    private static Task createTaskFromStorage(String type, ArrayList<String> tags,
                                              String[] parts, String description) {
        switch (type) {
        case TYPE_TODO:
            return new Todo(description, tags);
        case TYPE_DEADLINE:
            return createDeadlineFromStorage(parts, tags, description);
        case TYPE_EVENT:
            return createEventFromStorage(parts, tags, description);
        default:
            return null;
        }
    }

    /**
     * Helper to create a Deadline object from storage parts.
     *
     * @param parts The split line array.
     * @param description The task description.
     * @return A new Deadline object.
     */
    private static Task createDeadlineFromStorage(String[] parts, ArrayList<String> tags, String description) {
        LocalDate deadline = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern(DATE_PATTERN));
        return new Deadline(description, tags, deadline);
    }

    /**
     * Helper to create an Event object from storage parts.
     *
     * @param parts The split line array.
     * @param description The task description.
     * @return A new Event object.
     */
    private static Task createEventFromStorage(String[] parts, ArrayList<String> tags, String description) {
        return new Event(description, tags, parts[4], parts[5]);
    }
}
