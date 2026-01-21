import java.util.ArrayList;
import java.util.Scanner;
public class James {
    public static void main(String[] args) {
        String divider = "  ____________________________________________________________";
        ArrayList<String> list = new ArrayList<>();

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
            }else if (input.equalsIgnoreCase("list")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
            }else {
                list.add(input);
                System.out.println("  added: " + input);
            }
            System.out.println(divider);
        }
        sc.close();
    }
}