package james;

import james.exception.*;
import james.task.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String divider = "  ____________________________________________________________";
    private String filePath;
    private Scanner sc;


    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public Ui(String filePath) throws JamesException {
        try {
            this.filePath = filePath;
            File file = new File(filePath);
            this.sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new CanNotFindFileException(e.getMessage());
        }
    }

    public void printDivider() {
        System.out.println(divider);
    }

    public void greet() {
        printDivider();
        System.out.println("  Wassup! This is James.");
        System.out.println("  What can I do for you?");
        printDivider();
    }

    public String readCommand() {
        return this.sc.nextLine().trim();
    }

    public void bye() {
        System.out.println("  You take care bro. Hope to see you again soon!");
    }

    public ArrayList<String> readFile() {
        ArrayList<String> list = new ArrayList<>();
        while (this.sc.hasNextLine()) {
            list.add(this.sc.nextLine().trim());
        }
        return list;
    }

    public void writeToFile(TaskList tasks) throws JamesException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (int i = 1; i <= tasks.getSize(); i++) {
                fw.write(tasks.getTask(i).toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e){
            throw new CanNotWriteToFileException(e.getMessage());
        }
    }

    public void showJamesError(JamesException je) {
        System.out.println("  " + je.getMessage());
    }

    public void printList(TaskList taskList) {
        System.out.println("  Here you go bro! Here's your list.");
        taskList.printTasks(this);
    }

    public void markTask(TaskList tasks, int taskNumber) {
        System.out.println("  No problem man! I've marked this task as done:");
        tasks.printTask(this, taskNumber);

    }

    public void unmarkTask(TaskList tasks, int taskNumber) {
        System.out.println("  I got you bro! I've marked this task as not done yet:");
        tasks.printTask(this, taskNumber);
    }

    public void addTask(TaskList taskList) {
        System.out.println("  Say less bro. I've added this task for you:");
        taskList.printTask(this, taskList.getSize());
        System.out.println("  Now you have " + taskList.getSize() + " tasks in your list!");
    }

    public void deleteTask1(TaskList tasks, int taskNumber) {
        System.out.println("  You bet dawg. I have removed this task for you:");
        tasks.printTask(this, taskNumber);
    }

    public void deleteTask2(TaskList tasks) {
        System.out.println("  Now you have " + tasks.getSize() + " tasks in your list!");
    }

    public void printTask(Task task) {
        System.out.println("    " + task.toString());
    }

    public void printTasks(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + "." + tasks.get(i).toString());
        }
    }
}