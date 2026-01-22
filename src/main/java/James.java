import java.util.ArrayList;
import java.util.Scanner;

public class James {
    public static void main(String[] args) {
        String divider = "  ____________________________________________________________";
        TaskList taskList = new TaskList();

        System.out.println(divider);
        System.out.println("  Wassup! This is James.");
        System.out.println("  What can I do for you?");
        System.out.println(divider);

        Scanner sc = new Scanner (System.in);

        while (true) {
            String input = sc.nextLine();
            String[] words = input.split(" ");
            System.out.println(divider);

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("  You take care bro. Hope to see you again soon!");
                System.out.println(divider);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("  Here you go bro! Here's your list.");
                taskList.printTasks();
            } else if (words[0].equalsIgnoreCase("mark")) {
                if (words.length < 2) {
                    System.out.println("  Bro, you need to tell me which number to mark!");
                } else {
                    try {
                        int taskNumber = Integer.parseInt(words[1]);
                        if (taskNumber >= 0 && taskNumber < taskList.getSize()) {
                            taskList.markTask(taskNumber);
                            System.out.println("  No problem man! I've marked this task as done:");
                            taskList.printTask(taskNumber);
                        } else {
                            System.out.println("  Nah bro, that task number doesn't exist!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("  Chill, that's not a number! Use something like 'mark 1'.");
                    }
                }
            } else if (words[0].equalsIgnoreCase("unmark")) {
                if (words.length < 2) {
                    System.out.println("  Bro, you need to tell me which number to unmark!");
                } else {
                    try {
                        int taskNumber = Integer.parseInt(words[1]);
                        if (taskNumber >= 0 && taskNumber < taskList.getSize()) {
                            taskList.unmarkTask(taskNumber);
                            System.out.println("  I got you bro! I've marked this task as not done yet:");
                            taskList.printTask(taskNumber);
                        } else {
                            System.out.println("  Nah bro, that task number doesn't exist!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("  Chill, that's not a number! Use something like 'unmark 1'.");
                    }
                }
            } else {
                String type = words[0];
                if (type.equals("todo") || type.equals("deadline") || type.equals("event")) {
                    if (input.split(" ", 2).length < 2) {
                        System.out.println("  Bro, the description of a " + type + " cannot be empty!");
                    } else {
                        String description = input.split(" ", 2)[1];
                        Task task = null;
                        if (type.equals("todo")) {
                            task = new Todo(description);
                        } else if (type.equals("deadline")) {
                            String[] parts = description.split(" /by ", 2);
                            if (parts.length == 2) {
                                task = new Deadline(parts[0], parts[1]);
                            } else {
                                System.out.println("  Bro, use: deadline [desc] /by [time]");
                            }
                        } else if (type.equals("event")) {
                            try {
                                String info = description.split(" /from ")[0];
                                String times = description.split(" /from ")[1];
                                String startTime = times.split(" /to ")[0];
                                String endTime = times.split(" /to ")[1];
                                task = new Event(info, startTime, endTime);
                            } catch (Exception e) {
                                System.out.println("  Bro, use: event [desc] /from [start] /to [end]");
                            }
                        }

                        if (task != null) {
                            taskList.addTask(task);
                            System.out.println("  Say less bro, I've added this task for you:");
                            int numOfTasks = taskList.getSize();
                            taskList.printTask(numOfTasks);
                            System.out.println("  Now you have " + numOfTasks + " tasks in your list!");
                        }
                    }
                } else {
                    System.out.println("  Hey bro, I don't know what that means!");
                }
            }
            System.out.println(divider);
        }
        sc.close();
    }
}