import java.util.Scanner;

public class Jung {

    //given that there is no more than 100 task so can use String[100]
    private static Task[] tasks = new Task[100];
    private static int taskCount =  0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //scans in any input by user
        System.out.println("Hello! I'm Jung.");
        System.out.println("I don't really want to help but what can I do for you today?");
        System.out.println(); //blank line
        //up till this point nothing should be changed here anymore

        while (true) {
            String input = scanner.nextLine();
            try {
                if (input.isEmpty()) {
                    throw new JungException("Input cannot be empty. Please enter a valid command.");
                }
                //exit if user says bye
                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("Bye. I hope I never see you again..");
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    if (taskCount == 0) {
                        System.out.println("Your task list is currently empty.");
                    }
                    //otherwise repeat the tasks to them and number them if the user inputs "list"
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                        //must start from i = 0 oops because otherwise, when u run it starts from the 2nd
                        //item that you add to the list if start from i = 1
                    }
                    System.out.println();
                } else if (input.startsWith("mark")) {
                    String numberStr = input.substring(4).trim();
                    if (numberStr.isEmpty()) {
                        throw new JungException("Mark requires a task number. Example mark 1");
                    }
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    //get the substring starting from char at index 5 till end, then trim away
                    //then convert the remaining int into string, -1 is for the indexing later on
                    if (!validIndex(index)) {
                        throw new JungException("Invalid task number. Please enter a valid number.");
                    }
                    tasks[index].markAsDone();
                    System.out.println("Lame, this task is marked as done.");
                    System.out.println(tasks[index]);
                    System.out.println();
                } else if (input.startsWith("unmark")) {
                    String numberStr = input.substring(6).trim();
                    if (numberStr.isEmpty()) {
                        throw new JungException("unmark requires a task number. Example: unmark 1");
                    }
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (!validIndex(index)) {
                        throw new JungException("invalid task number for unmarking. Please enter a valid number.");
                    }
                    tasks[index].markAsNotDone();
                    System.out.println("I've marked this task as not done yet.");
                    System.out.println(tasks[index]);
                    System.out.println();
                } else if (input.startsWith("todo")) {
                    String desc = input.length() > 4 ? input.substring(4).trim() : "";
                    if (desc.isEmpty()) {
                        throw new JungException("The description of a todo cannot be empty. Try: todo [description]");
                    }
                    addTask(new ToDo(desc));
                } else if (input.startsWith("deadline")) {
                    //format: "deadline desc /by "
                    int byIndex = input.indexOf("/by");
                    if (byIndex == -1) {
                        throw new JungException("Deadline task requires a '/by' date/time. Format: deadline [desc] /by [datetime]");
                    }
                    String desc = input.substring(8, byIndex).trim();
                    String by = input.substring(byIndex + 3).trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        throw new JungException("Deadline description or date/time cannot be empty.");
                    }
                    addTask(new Deadline(desc, by));
                } else if (input.startsWith("event")) {
                    //format: "event desc /from start /to end"
                    int fromIndex = input.indexOf("/from");
                    int toIndex = input.indexOf("/to");
                    if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                        throw new JungException("Event task requires both '/from' and '/to' date/time. Format: event [desc] /from [start] /to [end]");
                    }
                    //retrieving the relevant info from user input
                    String desc = input.substring(5, fromIndex).trim();
                    String from = input.substring(fromIndex + 5, toIndex).trim();
                    String to = input.substring(toIndex + 3).trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new JungException("Event description, start, and end date/time cannot be empty.");
                    }
                    addTask(new Event(desc, from, to));
                } else {
                    throw new JungException("Sorry, I don't recognise that command. Please try again.");
                }
            } catch (JungException e) {
                System.out.println("Oops! " + e.getMessage());
                System.out.println();
            }
        }
    }

    private static void addTask(Task task) {
        if (taskCount < tasks.length) {
            tasks[taskCount] = task;
            taskCount++;
            System.out.println("Okay. I've added this task:");
            System.out.println(task);
            System.out.println("You now have " + taskCount + " tasks in the list.");
            System.out.println();
        } else {
            System.out.println("Task list full. Cannot add any more tasks.");
        }
    }

    private static boolean validIndex(int index) {
        return index >= 0 && index < taskCount;
    }
}
