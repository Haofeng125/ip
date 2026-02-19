package james;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import james.exception.JamesException;
import james.task.Todo;

/**
 * JUnit test class for Storage.
 * Verifies that tasks are correctly saved to a file and loaded back.
 */
public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_validTasks_success() throws JamesException {
        File tempFile = tempDir.resolve("test_james.txt").toFile();
        Storage storage = new Storage(tempFile.getAbsolutePath());

        TaskList tasksToSave = new TaskList();
        // Assuming Todo has a constructor Todo(String description)
        tasksToSave.addTask(new Todo("test task 1"));
        tasksToSave.addTask(new Todo("test task 2"));

        storage.saveTasks(tasksToSave);
        TaskList loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.getSize(), "Should load exactly 2 tasks.");
        // We check if the descriptions are correct.
        // Note: getTask(i) is 1-indexed in your TaskList based on previous files
        assertTrue(loadedTasks.getTask(1).toString().contains("test task 1"));
        assertTrue(loadedTasks.getTask(2).toString().contains("test task 2"));
    }

    @Test
    public void loadTasks_manualFileCreation_success() throws JamesException, IOException {
        Path file = tempDir.resolve("manual_test_james.txt");
        // Manually write lines simulating the 4-column storage format
        // Ensure this format matches what your Parser expects (T | Tags | Status | Description)
        List<String> lines = List.of(
                "T |  | 1 | manual task 1",
                "T |  | 0 | manual task 2"
        );
        Files.write(file, lines);

        Storage storage = new Storage(file.toAbsolutePath().toString());
        TaskList loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.getSize(), "Should load 2 tasks from manual file.");
        assertTrue(loadedTasks.getTask(1).toString().contains("manual task 1"));
    }

    @Test
    public void loadTasks_nonExistentFile_throwsException() {
        Storage storage = new Storage(tempDir.resolve("non_existent_file.txt").toString());

        // Assuming your Storage throws a CanNotFindFileException or similar JamesException
        assertThrows(Exception.class, () -> {
            storage.loadTasks();
        }, "Should throw an exception when the file is missing.");
    }
}
