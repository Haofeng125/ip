import java.util.Scanner;
public class James {
    public static void main(String[] args) {
        String divider = "  ____________________________________________________________";

        System.out.println(divider);
        System.out.println("  Wassup! This is James.");
        System.out.println("  What can I do for you?");
        System.out.println(divider);

        Scanner sc = new Scanner (System.in);

        while (true) {
            String input = sc.nextLine();
            System.out.println(divider);

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("  You take care bro. Hope to see you again soon!");
                System.out.println(divider);
                break;
            }

            System.out.println("  " + input);
            System.out.println(divider);
        }
        sc.close();
    }
}