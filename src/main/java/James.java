import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import exceptions.*;
import tasks.*;

/**
 * Represents the main entry point for the James chatbot application.
 * James is a task management tool that handles todos, deadlines, and events.
 * James can also handle jobs such as mark, unmark and delete.
 */
public class James {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public James(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        try {
            this.taskList = this.storage.loadTasks();
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            this.taskList = new TaskList();
        }
    }

    public void run() {
        ui.greeting();
        while (true) {
            try {
                String fullCommand = ui.readCommand();
                if (fullCommand.isEmpty()) {
                    continue;
                }

                String[] words = fullCommand.split(" ", 2);
                String command = words[0].toLowerCase();

                if (command.equals("bye")) {
                    this.storage.saveTasks(this.taskList);
                    this.ui.bye();
                    break;
                }

                Ui.printDivider();

                if (command.equals("list")) {
                    ui.printList(this.taskList);
                } else if (command.equals("mark") || command.equals("unmark")) {
                    handleMarking(command, words, taskList);
                } else if (command.equals("delete")) {
                    handleDeletion(command, words, taskList);
                } else {
                    handleTaskCreation(command, words, taskList, fullCommand);
                }
            } catch (JamesException e) {
                ui.showJamesError(e);
            } catch (NumberFormatException e) {
                ui.showNumberError();
            } catch (DateTimeParseException e) {
                ui.showDateError();
            } catch (Exception e) {
                ui.showError(e);
            }

            Ui.printDivider();
        }
    }

    // Main entry point that runs the chatbot and handles the user input loop
    public static void main(String[] args) {
        new James("./data/tasks.txt").run();
    }

    // Updates a task's status to done or not done based on the index provided
    private static void handleMarking(String command, String[] words, TaskList taskList) throws JamesException {
        if (words.length < 2) {
            throw new JamesException("you need to tell me which number to " + command + "!");
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber <= 0 || taskNumber > taskList.getSize()) {
            throw new JamesException("that task number doesn't exist!");
        }

        if (command.equals("mark")) {
            taskList.markTask(taskNumber);
            Ui.markTask();
        } else {
            taskList.unmarkTask(taskNumber);
            Ui.unmarkTask();
        }
        taskList.printTask(taskNumber);
    }

    // Parses input to create and add a Todo, Deadline, or Event to the list
    private static void handleTaskCreation(String command, String[] words, TaskList taskList, String input) throws JamesException {
        if (!command.equals("todo") && !command.equals("deadline") && !command.equals("event")) {
            throw new UnknownCommandException(input);
        }

        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new EmptyDescriptionException(command);
        }

        String description = words[1];
        Task task = null;

        if (command.equals("todo")) {
            task = new Todo(description);
        } else if (command.equals("deadline")) {
            String[] parts = description.split(" /by ", 2);
            if (parts.length < 2) throw InvalidFormatException.forDeadline();
            task = new Deadline(parts[0], parts[1]);
        } else if (command.equals("event")) {
            if (!description.contains(" /from ") || !description.contains(" /to ")) {
                throw InvalidFormatException.forEvent();
            }
            String info = description.split(" /from ")[0];
            String times = description.split(" /from ")[1];
            String startTime = times.split(" /to ")[0];
            String endTime = times.split(" /to ")[1];
            task = new Event(info, startTime, endTime);
        }

        if (task != null) {
            taskList.addTask(task);
            Ui.addTask(taskList);
        }
    }

    // Removes a specific task from the list using its list index
    private static void handleDeletion(String command, String[] words, TaskList taskList) throws JamesException {
        if (words.length < 2) {
            throw new JamesException("you need to tell me which number to " + command + "!");
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber <= 0 || taskNumber > taskList.getSize()) {
            throw new JamesException("that task number doesn't exist!");
        }
        System.out.println("  You bet dawg. I have removed this task for you:");
        taskList.printTask(taskNumber);
        taskList.removeTask(taskNumber);
        System.out.println("  Now you have " + taskList.getSize() + " tasks in your list!");
    }
}