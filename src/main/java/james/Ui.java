package james;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import james.exception.CanNotFindFileException;
import james.exception.CanNotWriteToFileException;
import james.exception.JamesException;
import james.task.Task;

/**
 * Handles all interactions with the user and the file system.
 * This class is responsible for printing formatted messages to the console,
 * reading user input, and performing low-level file I/O operations.
 */
public class Ui {
    /** The decorative line used to separate message blocks in the console. */
    private static final String DIVIDER = "  ____________________________________________________________";
    private String filePath;
    private Scanner scanner;
    private final StringBuilder responseBuffer = new StringBuilder();

    /**
     * Initializes an Ui object for standard console input/output.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initializes an Ui object for reading data from a specific file.
     *
     * @param filePath The path to the file to be read.
     */
    public Ui(String filePath) {
        assert filePath != null : "Filepath cannot be null.";
        this.filePath = filePath;
    }

    /**
     * Internal helper to route all text to both the GUI buffer and the System console.
     */
    private void appendAndPrint(String message) {
        assert message != null : "Cannot append to a null message";
        responseBuffer.append(message).append("\n");
        System.out.println("  " + message);
    }

    /**
     * Prints a horizontal divider line to the console.
     */
    public void printDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays the greeting message when the application starts.
     */
    public void greet() {
        printDivider();
        printGreetMessage();
        printDivider();
    }

    /**
     * Append and print the greeting message
     */
    public void printGreetMessage() {
        appendAndPrint("Wassup! This is James.");
        appendAndPrint("What can I do for you?");
    }

    /**
     * Reads and trims the next line of input from the user.
     *
     * @return The trimmed command string.
     */
    public String readCommand() {
        if (this.scanner.hasNextLine()) {
            return this.scanner.nextLine().trim();
        }
        return "";
    }

    /**
     * Displays the exit message when the application terminates.
     */
    public void bye() {
        appendAndPrint("You take care bro. Hope to see you again soon!");
    }

    /**
     * Reads all lines from the current file scanner into a list of strings.
     *
     * @return An ArrayList of strings, each representing a line in the file.
     */
    public ArrayList<String> readFile() throws JamesException {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(this.filePath);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine().trim());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new CanNotFindFileException("File missing at: " + filePath);
        }
        return lines;
    }

    /**
     * Writes the current TaskList to the data file in a machine-readable format.
     *
     * @param tasks The TaskList containing tasks to be saved.
     * @throws JamesException If an error occurs during the writing process.
     */
    public void writeToFile(TaskList tasks) throws JamesException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Creates 'data' folder if it disappeared
            }
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 1; i <= tasks.getSize(); i++) {
                fileWriter.write(tasks.getTask(i).toFileFormat() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new CanNotWriteToFileException(e.getMessage());
        }
    }

    /**
     * Prints an error message specifically related to James chatbot operations.
     *
     * @param je The JamesException containing the error message.
     */
    public void showJamesError(JamesException je) {
        assert je != null : "Cannot show James error.";
        appendAndPrint(je.getMessage());
    }

    /**
     * Displays all tasks currently in the list to the user.
     *
     * @param taskList The TaskList to be displayed.
     */
    public void printList(TaskList taskList) {
        assert taskList != null : "Cannot print a null list";
        appendAndPrint("Here you go bro! Here's your list.");
        taskList.printTasks(this);
    }

    /**
     * Confirms that a task has been successfully marked as done.
     *
     * @param tasks The TaskList containing the task.
     * @param taskNumber The 1-indexed position of the task.
     */
    public void markTask(TaskList tasks, int taskNumber) {
        assert tasks != null : "Cannot mark a null list";
        assert taskNumber <= 0 || taskNumber > tasks.getSize() : "Task index out of bounds";
        appendAndPrint("No problem man! I've marked this task as done:");
        tasks.printTask(this, taskNumber);
    }

    /**
     * Confirms that a task has been successfully unmarked.
     *
     * @param tasks The TaskList containing the task.
     * @param taskNumber The 1-indexed position of the task.
     */
    public void unmarkTask(TaskList tasks, int taskNumber) {
        assert tasks != null : "Cannot mark a null list";
        assert taskNumber <= 0 || taskNumber > tasks.getSize() : "Task index out of bounds";
        appendAndPrint("I got you bro! I've marked this task as not done yet:");
        tasks.printTask(this, taskNumber);
    }

    /**
     * Confirms that a new task has been added and displays the new list size.
     *
     * @param taskList The TaskList after the addition.
     */
    public void addTask(TaskList taskList) {
        assert taskList != null : "Cannot add a null list";
        appendAndPrint("Say less bro. I've added this task for you:");
        taskList.printTask(this, taskList.getSize());
        appendAndPrint("Now you have " + taskList.getSize() + " tasks in your list!");
    }

    /**
     * Showing the confirmation message of the removal of a task.
     *
     * @param deletedTask The task we just deleted.
     * @param remainingSize The size of task list after the deletion.
     */
    public void deleteTask(Task deletedTask, int remainingSize) {
        assert deletedTask != null : "Cannot delete a null list";
        assert remainingSize >= 0 : "Cannot delete a null list";
        appendAndPrint("You bet man. I have removed this task for you:");
        printTask(deletedTask);
        appendAndPrint("Now you have " + remainingSize + " tasks in your list!");
    }

    /**
     * Prints the string representation of a single task with indentation.
     *
     * @param task The task to be printed.
     */
    public void printTask(Task task) {
        assert task != null : "Cannot print a null list";
        appendAndPrint("  " + task.toString());
    }

    /**
     * Prints a numbered list of tasks.
     *
     * @param tasks The list of Task objects to be printed.
     */
    public void printTasks(ArrayList<Task> tasks) {
        assert tasks != null : "Cannot print a null list";
        for (int i = 0; i < tasks.size(); i++) {
            appendAndPrint((i + 1) + "." + tasks.get(i).toString());
        }
    }

    /**
     * Prints a numbered list of tasks that match a keyword.
     *
     * @param tasks The list of Task objects to search through.
     * @param keyWord The keyWord to search for in tasks.
     */
    public void findTask(ArrayList<Task> tasks, String keyWord) {
        assert tasks != null : "Task list cannot be null";
        assert keyWord != null : "Keyword cannot be null";
        appendAndPrint("Bro, here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.toString().toLowerCase().contains(keyWord.toLowerCase())) {
                this.printTask(task);
            }
        }
    }

    /**
     * Showing the confirmation message of tagging a task.
     *
     * @param tasks The TaskList containing the task.
     * @param taskNumber The 1-indexed position of the task.
     */
    public void tagTask(TaskList tasks, int taskNumber) {
        assert tasks != null : "Task list cannot be null";
        appendAndPrint("No problem man. I've tagged this task for you:");
        printTask(tasks.getTask(taskNumber));
    }

    /**
     * Clears the buffer and returns the collected string for the GUI.
     */
    public String getAndClearResponse() {
        String result = responseBuffer.toString().trim();
        responseBuffer.setLength(0);
        return result;
    }
}
