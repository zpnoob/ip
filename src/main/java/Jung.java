import java.util.Scanner;

public class Jung {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //scans in any input by user
        System.out.println("Hello! I'm Jung.");
        System.out.println("I don't really want to help but what can I do for you today?");
        //up till this point nothing should be changed here anymore

        //given that there is no more than 100 task so can use String[100]
        String[] tasks = new String[100];
        int taskCount =  0;

        while (true) {
            String input = scanner.nextLine();
            //exit if user says bye
            if (input.equals("bye")) {
                System.out.println("Bye. I hope I never see you again..");
                break;
            } else if (input.equals("list")){
                //otherwise repeat the tasks to them and number them if the user inputs "list"
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                    //must start from i = 0 oops because otherwise, when u run it starts from the 2nd
                    //item that you add to the list if start from i = 1
                }
            } else {
                // keep adding in tasks otherwise
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("added: " + input);
            }
        }
    }
}
