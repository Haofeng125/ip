package james;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import james.exception.JamesException;
import james.task.Task;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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