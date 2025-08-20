import java.util.Scanner;

public class Jung {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //scans in any input by user

        System.out.println("Hello! I'm Jung.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();
            System.out.println(input);
            //exit if user says bye
            if (input.equals("bye")) {
                System.out.println("Bye. I hope I never see you again..");
                break;
            }
        }
    }
}
