import java.util.ArrayList;
import java.util.Scanner;
public class James {
    public static void main(String[] args) {
        String divider = "  ____________________________________________________________";
        ArrayList<Task> list = new ArrayList<>();

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
            }else if (input.equalsIgnoreCase("list")) {
                System.out.println("  Here you go bro! Here's your list.");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("  " + (i + 1) + "." + list.get(i).toString());
                }
            } else if (words[0].equalsIgnoreCase("mark")) {
                if (words.length < 2) {
                    System.out.println("  Bro, you need to tell me which number to mark!");
                } else {
                    try {
                        int number = Integer.parseInt(words[1]) - 1;
                        if (number >= 0 && number < list.size()) {
                            list.get(number).mark();
                            System.out.println("  No problem man! I've marked this task as done:");
                            System.out.println("    " + list.get(number).toString());
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
                        int number = Integer.parseInt(words[1]) - 1;
                        if (number >= 0 && number < list.size()) {
                            list.get(number).unmark();
                            System.out.println("  I got you bro! I've marked this task as not done yet:");
                            System.out.println("    " + list.get(number).toString());
                        } else {
                            System.out.println("  Nah bro, that task number doesn't exist!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("  Chill, that's not a number! Use something like 'unmark 1'.");
                    }
                }
            }else {
                list.add(new Task(input));
                System.out.println("  added: " + input);
            }
            System.out.println(divider);
        }
        sc.close();
    }
}