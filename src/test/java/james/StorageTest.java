package james;

import james.task.Todo;
import james.exception.JamesException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_validTasks_success() throws JamesException {
        File tempFile = tempDir.resolve("test_james.txt").toFile();
        Storage storage = new Storage(tempFile.getAbsolutePath());

        TaskList tasksToSave = new TaskList();
        tasksToSave.addTask(new Todo("test task 1"));
        tasksToSave.addTask(new Todo("test task 2"));

        storage.saveTasks(tasksToSave);
        TaskList loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.getSize(), "Should load exactly 2 tasks.");
        assertEquals("[T][ ] test task 1", loadedTasks.getTask(1).toString());
        assertEquals("[T][ ] test task 2", loadedTasks.getTask(2).toString());
    }

    @Test
    public void loadTasks_nonExistentFile_throwsException() {
        Storage storage = new Storage(tempDir.resolve("non_existent_file.txt").toString());

        assertThrows(james.exception.CanNotFindFileException.class, () -> {
            storage.loadTasks();
        }, "Should throw CanNotFindFileException when the file is missing.");
    }
}