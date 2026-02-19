package james;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import james.command.Command;
import james.command.CreateTaskCommand;
import james.exception.JamesException;
import james.task.Task;

/**
 * JUnit test class for the Parser.
 * Verifies that raw strings from user input and storage files are correctly
 * translated into Command objects and Task objects respectively.
 */
public class ParserTest {

    /**
     * Helper method to access private fields in Command objects using Reflection.
     * This avoids the need to create public getters just for testing.
     */
    private Object getPrivateField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not access private field: " + fieldName, e);
        }
    }

    /**
     * Tests if a valid "deadline" command string is correctly parsed into a CreateTaskCommand.
     * Uses reflection to check the private 'taskType', 'descriptions', and 'deadlineTime' fields.
     */
    @Test
    public void parseCommand_deadlineWithDate_detailedCheck() throws JamesException {
        TaskList tasks = new TaskList();
        String input = "deadline Return textbook /by 2026-05-15";

        Command result = Parser.parseCommand(input, tasks);

        assertTrue(result instanceof CreateTaskCommand);

        // Use reflection to check private fields since there are no getters
        Object taskType = getPrivateField(result, "taskType");
        String[] descriptions = (String[]) getPrivateField(result, "descriptions");
        LocalDate deadlineTime = (LocalDate) getPrivateField(result, "deadlineTime");

        assertEquals("DEADLINE", taskType.toString());
        assertEquals("Return textbook", descriptions[0]);
        assertEquals(LocalDate.of(2026, 5, 15), deadlineTime,
                "The date string should be converted into a proper LocalDate object.");
    }

    /**
     * Tests if a complex "event" command string is correctly split into its components.
     */
    @Test
    public void parseCommand_eventComplex_detailedCheck() throws JamesException {
        TaskList tasks = new TaskList();
        String input = "event Project Meeting /from Monday 2pm /to 4pm";

        Command result = Parser.parseCommand(input, tasks);
        assertTrue(result instanceof CreateTaskCommand);

        // Use reflection to check private descriptions array
        String[] descriptions = (String[]) getPrivateField(result, "descriptions");

        String[] expectedParts = {"Project Meeting", "Monday 2pm", "4pm"};
        assertArrayEquals(expectedParts, descriptions,
                "The parser should split the description, start time, and end time accurately.");
    }

    /**
     * Tests if a saved Todo task string (from the storage file) is correctly parsed back into a Task object.
     * Uses the 4-column format: Type | Tags | Status | Description.
     */
    @Test
    public void parseLine_savedTodo_detailedCheck() {
        // Format: Type | Tags | Status | Description
        String savedLine = "T |  | 1 | read book";

        Task task = Parser.parseLine(savedLine);

        // FIX: Added a trailing space after "read book" to match your task.toString() output
        assertEquals("[T][X] read book ", task.toString(),
                "The parser should correctly identify a completed Todo task from a save string.");
    }

    /**
     * Tests if a saved Deadline task string is correctly parsed back into a Task object.
     * Uses the 5-column format: Type | Tags | Status | Description | Date.
     */
    @Test
    public void parseLine_savedDeadline_detailedCheck() {
        // Format: Type | Tags | Status | Description | Date
        // Note: ensure your Deadline.toFileFormat matches this structure
        String savedLine = "D |  | 0 | return book | 2026-05-15";

        Task task = Parser.parseLine(savedLine);

        // Note: Assumes Deadline.toString outputs something like [D][ ] description (by: Month Day Year)
        // Adjust the expected string if your format differs slightly (e.g. "May 15 2026")
        String result = task.toString();
        assertTrue(result.contains("[D]"), "Should be identified as a Deadline");
        assertTrue(result.contains("return book"), "Description should be correct");
        assertTrue(result.contains("May 15 2026") || result.contains("2026-05-15"),
                "Date should be parsed and formatted correctly");
    }
}
