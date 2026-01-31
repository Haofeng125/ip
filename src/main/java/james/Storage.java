package james;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import james.exception.JamesException;
import james.task.Task;

/**
 * Handles the loading and saving of task data to the hard disk.
 * This class is responsible for file creation and management of the data file
 * used to store the user's task list.
 */
public class Storage {
    private String filePath;

    /**
     * Initializes a Storage object with a specific file path.
     *
     * @param filePath The relative or absolute path where the data file is located.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file specified by the file path.
     * Reads the file line by line and uses the Parser to reconstruct Task objects.
     *
     * @return A TaskList containing the tasks loaded from the file.
     * @throws JamesException If there is an error reading the file or parsing data.
     */
    public TaskList loadTasks() throws JamesException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui(this.filePath);
        ArrayList<String> lines = ui.readFile();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            Task task = Parser.parseLine(line);
            if (task != null) {
                tasks.addTask(task);
            }
        }

        return tasks;
    }

    /**
     * Saves the current list of tasks to the hard disk.
     * Creates the directory and file if they do not exist before writing.
     *
     * @param tasks The TaskList containing tasks to be saved.
     * @throws JamesException If the file cannot be created or written to.
     */
    public void saveTasks(TaskList tasks) throws JamesException {
        File file = new File(filePath);

        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new JamesException("Could not create file");
        }

        Ui ui = new Ui(this.filePath);
        ui.writeToFile(tasks);
    }
}