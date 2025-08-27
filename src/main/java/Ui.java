import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Jung.");
        System.out.println("I don't really want to help but bopes, what can " +
                "I do for you today?");
        System.out.println();
    }

    public void showSpace() {
        System.out.println();
    }

    public void showLoadingError() {
        System.out.println("Failed to load tasks, starting with empty list.");
    }

    public void showError(String message) {
        System.out.println("Oops! " + message);
        showSpace();
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    // Additional helper methods to show confirmation or task lists

    public void showTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is currently empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        showSpace();
    }

    public void showAddTask(Task task, int size) {
        System.out.println("Okay. I've added this task:");
        System.out.println(task);
        System.out.println("You now have " + size + " tasks in the list.");
        showSpace();
    }

    public void showDeleteTask(Task task, int size) {
        System.out.println("Okay. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showSpace();
    }

    public void showMark(Task task) {
        System.out.println("Lame, this task is marked as done.");
        System.out.println(task);
        System.out.println();
    }

    public void showUnmark(Task task) {
        System.out.println("I've marked this task as not done yet.");
        System.out.println(task);
        System.out.println();
    }
}
