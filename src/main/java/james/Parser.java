package james;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import james.command.Command;
import james.command.CreateTaskCommand;
import james.command.DeleteTaskCommand;
import james.command.ListCommand;
import james.command.MarkCommand;
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
     * Parses the raw user input into a specific Command object.
     *
     * @param fullCommand The full line of text entered by the user.
     * @param tasks The current list of tasks, used for range validation.
     * @return A Command object corresponding to the user's intent.
     * @throws JamesException If the command is unknown, the format is incorrect,
     * or the task index is out of bounds.
     */
    public static Command parseCommand(String fullCommand, TaskList tasks) throws JamesException {
        String[] words = fullCommand.split(" ", 2);
        String command = words[0].toLowerCase();
        String description;

        if (words.length > 1) {
            description = words[1];
        } else {
            description = "";
        }

        if (command.equals("bye")) {
            return new TerminateProgramCommand();
        } else if (command.equals("mark") || command.equals("unmark") || command.equals("delete")) {
            if (words.length < 2) {
                throw InvalidNumberException.forMissingNumber(command);
            }

            int taskNumber;
            try {
                taskNumber = Integer.parseInt(words[1]);
            } catch (NumberFormatException e) {
                throw InvalidNumberException.forNotANumber();
            }

            if (taskNumber <= 0 || taskNumber > tasks.getSize()) {
                throw InvalidNumberException.forNumberOutOfRange();
            }

            if (command.equals("mark")) {
                return new MarkCommand(taskNumber);
            } else if (command.equals("unmark")) {
                return new UnmarkCommand(taskNumber);
            } else {
                return new DeleteTaskCommand(taskNumber);
            }
        } else if (command.equals("todo")) {
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new EmptyDescriptionException(command);
            }

            String[] parts = new String[1];
            parts[0] = description.trim();
            return new CreateTaskCommand(command, parts, null);
        } else if (command.equals("deadline")) {
            if (!description.contains(" /by ")) {
                throw InvalidFormatException.forDeadline();
            }

            String[] parts = description.split(" /by ", 2);
            if (parts.length < 2) {
                throw InvalidFormatException.forDeadline();
            }

            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate deadlineTime = LocalDate.parse(parts[1], dateFormatter);
                return new CreateTaskCommand(command, parts, deadlineTime);
            } catch (DateTimeParseException e) {
                throw InvalidFormatException.forLocalDate();
            }
        } else if (command.equals("event")) {
            if (!description.contains(" /from ") || !description.contains(" /to ")) {
                throw InvalidFormatException.forEvent();
            }

            String info = description.split(" /from ")[0];
            String times = description.split(" /from ")[1];
            String startTime = times.split(" /to ")[0];
            String endTime = times.split(" /to ")[1];

            String[] parts = new String[3];
            parts[0] = info;
            parts[1] = startTime;
            parts[2] = endTime;
            return new CreateTaskCommand(command, parts, null);
        } else if (command.equals("list")) {
            return new ListCommand();
        } else {
            throw new UnknownCommandException(fullCommand);
        }
    }

    /**
     * Parses a single line from the data storage file into a Task object.
     *
     * @param line The pipe-separated string representing a task in the storage file.
     * @return The reconstructed Task object, or null if the task type is unknown.
     */
    public static Task parseLine (String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0].toUpperCase();
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            LocalDate deadline = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            task = new Deadline(description, deadline);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            return null;
        }

        if (isDone) {
            task.mark();
        }

        return task;
    }
}