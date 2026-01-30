package james;

import james.command.Command;
import james.command.CreateTaskCommand;
import james.exception.JamesException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    public void parseCommand_deadlineWithDate_detailedCheck() throws JamesException {
        TaskList tasks = new TaskList();
        String input = "deadline Return textbook /by 2026-05-15";

        Command result = Parser.parseCommand(input, tasks);

        assertTrue(result instanceof CreateTaskCommand);
        CreateTaskCommand cmd = (CreateTaskCommand) result;

        assertEquals("deadline", cmd.getTaskType());
        assertEquals("Return textbook", cmd.getDescriptions()[0]);
        assertEquals(LocalDate.of(2026, 5, 15), cmd.getDeadlineTime(),
                "The date string should be converted into a proper LocalDate object.");
    }

    @Test
    public void parseCommand_eventComplex_detailedCheck() throws JamesException {
        TaskList tasks = new TaskList();
        String input = "event Project Meeting /from Monday 2pm /to 4pm";

        Command result = Parser.parseCommand(input, tasks);
        CreateTaskCommand cmd = (CreateTaskCommand) result;

        String[] expectedParts = {"Project Meeting", "Monday 2pm", "4pm"};
        assertArrayEquals(expectedParts, cmd.getDescriptions(),
                "The parser should split the description, start time, and end time accurately.");
    }

    @Test
    public void parseLine_savedTodo_detailedCheck() {
        String savedLine = "T | 1 | read book";

        james.task.Task task = Parser.parseLine(savedLine);

        assertEquals("[T][X] read book", task.toString(),
                "The parser should correctly identify a completed Todo task from a save string.");
    }
}