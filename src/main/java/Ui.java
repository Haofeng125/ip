import exceptions.*;
import tasks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String divider = "  ____________________________________________________________";
    Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public Ui(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        this.sc = new Scanner(file);
    }

    public static void printDivider() {
        System.out.println(divider);
    }

    public void greeting() {
        Ui.printDivider();
        System.out.println("  Wassup! This is James.");
        System.out.println("  What can I do for you?");
        Ui.printDivider();
    }

    public String readCommand() {
        return this.sc.nextLine().trim();
    }

    public void bye() {
        Ui.printDivider();
        System.out.println("  You take care bro. Hope to see you again soon!");
        Ui.printDivider();
    }

    public ArrayList<String> readFile() {
        ArrayList<String> list = new ArrayList<>();
        while (this.sc.hasNextLine()) {
            list.add(this.sc.nextLine().trim());
        }
        return list;
    }

    public void showLoadingError() {
        System.out.println("  bro, I can not load this file :(");
        System.out.println("  But no worries man, let me just create a new task list for you.");
    }

    public void showJamesError(JamesException je) {
        System.out.println("  " + je.getMessage());
    }

    public void showNumberError() {
        System.out.println("  Chill, that's not a number! Use something like 'mark 1'.");
    }

    public void showDateError() {
        System.out.println("  Hey man, you need to input your date in the format of 'yyyy-MM-dd'.");
    }

    public void showError(Exception e) {
        System.out.println("  Whoa bro, I ran into an unexpected error: " + e.getMessage());
    }

    public void printList(TaskList taskList) {
        System.out.println("  Here you go bro! Here's your list.");
        taskList.printTasks();
    }

    public static void markTask() {
        System.out.println("  No problem man! I've marked this task as done:");
    }

    public static void unmarkTask() {
        System.out.println("  I got you bro! I've marked this task as not done yet:");
    }

    public static void addTask(TaskList taskList) {
        System.out.println("  Say less bro. I've added this task for you:");
        taskList.printTask(taskList.getSize());
        System.out.println("  Now you have " + taskList.getSize() + " tasks in your list!");
    }

    public static void printTask(Task task) {
        System.out.println("    " + task.toString());
    }

    public static void printTasks(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + "." + tasks.get(i).toString());
        }
    }
}