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

    /**
     * Initializes a Ui object for standard console input/output.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initializes a Ui object for reading data from a specific file.
     *
     * @param filePath The path to the file to be read.
     * @throws JamesException If the specified file cannot be found.
     */
    public Ui(String filePath) throws JamesException {
        try {
            this.filePath = filePath;
            File file = new File(filePath);
            this.scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new CanNotFindFileException(e.getMessage());
        }
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
        System.out.println("  Wassup! This is James.");
        System.out.println("  What can I do for you?");
        printDivider();
    }

    /**
     * Reads and trims the next line of input from the user.
     *
     * @return The trimmed command string.
     */
    public String readCommand() {
        return this.scanner.nextLine().trim();
    }

    /**
     * Displays the exit message when the application terminates.
     */
    public void bye() {
        System.out.println("  You take care bro. Hope to see you again soon!");
    }

    /**
     * Reads all lines from the current file scanner into a list of strings.
     *
     * @return An ArrayList of strings, each representing a line in the file.
     */
    public ArrayList<String> readFile() {
        ArrayList<String> lines = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            lines.add(this.scanner.nextLine().trim());
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
            FileWriter fileWriter = new FileWriter(filePath);
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
        System.out.println("  " + je.getMessage());
    }

    /**
     * Displays all tasks currently in the list to the user.
     *
     * @param taskList The TaskList to be displayed.
     */
    public void printList(TaskList taskList) {
        System.out.println("  Here you go bro! Here's your list.");
        taskList.printTasks(this);
    }

    /**
     * Confirms that a task has been successfully marked as done.
     *
     * @param tasks The TaskList containing the task.
     * @param taskNumber The 1-indexed position of the task.
     */
    public void markTask(TaskList tasks, int taskNumber) {
        System.out.println("  No problem man! I've marked this task as done:");
        tasks.printTask(this, taskNumber);
    }

    /**
     * Confirms that a task has been successfully unmarked.
     *
     * @param tasks The TaskList containing the task.
     * @param taskNumber The 1-indexed position of the task.
     */
    public void unmarkTask(TaskList tasks, int taskNumber) {
        System.out.println("  I got you bro! I've marked this task as not done yet:");
        tasks.printTask(this, taskNumber);
    }

    /**
     * Confirms that a new task has been added and displays the new list size.
     *
     * @param taskList The TaskList after the addition.
     */
    public void addTask(TaskList taskList) {
        System.out.println("  Say less bro. I've added this task for you:");
        taskList.printTask(this, taskList.getSize());
        System.out.println("  Now you have " + taskList.getSize() + " tasks in your list!");
    }

    /**
     * Confirms the removal of a task.
     *
     * @param tasks The TaskList containing the task.
     * @param taskNumber The 1-indexed position of the task that was removed.
     */
    public void deleteTask1(TaskList tasks, int taskNumber) {
        System.out.println("  You bet dawg. I have removed this task for you:");
        tasks.printTask(this, taskNumber);
    }

    /**
     * Displays the updated task count after a deletion.
     *
     * @param tasks The TaskList after removal.
     */
    public void deleteTask2(TaskList tasks) {
        System.out.println("  Now you have " + tasks.getSize() + " tasks in your list!");
    }

    /**
     * Prints the string representation of a single task with indentation.
     *
     * @param task The task to be printed.
     */
    public void printTask(Task task) {
        System.out.println("    " + task.toString());
    }

    /**
     * Prints a numbered list of tasks.
     *
     * @param tasks The list of Task objects to be printed.
     */
    public void printTasks(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + "." + tasks.get(i).toString());
        }
    }

    /**
     * Prints a numbered list of tasks.
     *
     * @param tasks The list of Task objects to search through.
     * @param keyWord The keyWord to search for in tasks.
     */
    public void findTask(ArrayList<Task> tasks, String keyWord) {
        System.out.println("  Bro, here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.toString().toLowerCase().contains(keyWord.toLowerCase()))
                this.printTask(task);
        }
    }
}