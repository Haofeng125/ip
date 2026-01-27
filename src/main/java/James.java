import java.util.ArrayList;
import java.util.Scanner;
import exceptions.*;
import tasks.*;

/**
 * Represents the main entry point for the James chatbot application.
 * James is a task management tool that handles todos, deadlines, and events.
 * James can also handle jobs such as mark, unmark and delete.
 */
public class James {
    // Main entry point that runs the chatbot and handles the user input loop
    public static void main(String[] args) {
        String divider = "  ____________________________________________________________";
        String DataFilePath = "./data/tasks.txt";
        TaskList taskList;

        try {
            Storage storage = new Storage(DataFilePath);
            taskList = storage.loadTasks();
        } catch (Exception e) {
            taskList = new TaskList();
        }

        System.out.println(divider);
        System.out.println("  Wassup! This is James.");
        System.out.println("  What can I do for you?");
        System.out.println(divider);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] words = input.split(" ", 2);
                String command = words[0].toLowerCase();

                if (command.equals("bye")) {
                    Storage storage = new Storage(DataFilePath);
                    storage.saveTasks(taskList);
                    System.out.println(divider);
                    System.out.println("  You take care bro. Hope to see you again soon!");
                    System.out.println(divider);
                    break;
                }

                System.out.println(divider);

                if (command.equals("list")) {
                    System.out.println("  Here you go bro! Here's your list.");
                    taskList.printTasks();
                } else if (command.equals("mark") || command.equals("unmark")) {
                    handleMarking(command, words, taskList);
                } else if (command.equals("delete")) {
                    handleDeletion(command, words, taskList);
                } else {
                    handleTaskCreation(command, words, taskList, input);
                }
            } catch (JamesException e) {
                System.out.println("  " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("  Chill, that's not a number! Use something like 'mark 1'.");
            } catch (Exception e) {
                System.out.println("  Whoa bro, I ran into an unexpected error: " + e.getMessage());
            }
            System.out.println(divider);
        }
        sc.close();
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
            System.out.println("  No problem man! I've marked this task as done:");
        } else {
            taskList.unmarkTask(taskNumber);
            System.out.println("  I got you bro! I've marked this task as not done yet:");
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
            System.out.println("  Say less bro. I've added this task for you:");
            taskList.printTask(taskList.getSize());
            System.out.println("  Now you have " + taskList.getSize() + " tasks in your list!");
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