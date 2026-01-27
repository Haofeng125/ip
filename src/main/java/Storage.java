import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import tasks.*;
import exceptions.*;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public TaskList loadTasks() throws IOException {
        TaskList tasks = new TaskList();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            tasks.addTask(Storage.createTask(line));
        }
        scanner.close();
        return tasks;
    }

    private static Task createTask(String line) {
        String[] parts = line.split(" \\| ");
        String taskType = parts[0];
        boolean isCompleted = parts[1].equals("1");
        Task task;

        switch (taskType) {
        case "T":
            task = new Todo(parts[2]);
            break;
        case "D":
            task = new Deadline(parts[2], parts[3]);
            break;
        case "E":
            task = new Event(parts[2], parts[3], parts[4]);
            break;
        default:
            return null;
        }
        if (isCompleted) {
            task.mark();
        }
        return task;
    }

    public void saveTasks(TaskList tasks) throws IOException {
        File file = new File(filePath);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks.getTasks()) {
            fw.write(task.toFileFormat() + System.lineSeparator());
        }
        fw.close();
    }
}