package james;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import james.task.*;
import james.exception.*;

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
            if (line.trim().isEmpty()) continue;
            Task task = Parser.parseLine(line);
            if (task != null) {
                tasks.addTask(task);
            }
        }
        return tasks;
    }

    public void saveTasks(TaskList tasks) throws JamesException {
        File file = new File(filePath);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Ui ui = new Ui(this.filePath);
        ui.writeToFile(tasks);
    }
}