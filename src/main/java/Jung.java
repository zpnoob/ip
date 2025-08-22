import java.text.NumberFormat;
import java.util.Scanner;
import java.util.ArrayList;

public class Jung {

    //given that there is no more than 100 task so can use String[100]
    private static TaskList tasks = new TaskList();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //scans in any input by user
        System.out.println("Hello! I'm Jung.");
        System.out.println("I don't really want to help but what can I do for you today?");
        System.out.println();
        //up till this point nothing should be changed here anymore

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                if (input.isEmpty()) {
                    throw new JungException("Input cannot be empty. Please enter a valid command.");
                }

                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("Bye. I hope I never see you again..");
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    tasks.listTasks();
                }
                //delete command
                else if (input.startsWith("delete")) {
                    String argument = input.length() > 6 ? input.substring(6).trim() : "";
                    if (argument.isEmpty()) {
                        throw new JungException("Delete command requires a task number. Eg: delete 1");
                    }
                    int index = parseIndexFromCommand(argument);
                    tasks.deleteTask(index);
                }
                //mark command
                else if (input.toLowerCase().startsWith("mark")) {
                    String argument = input.length() > 4 ? input.substring(4).trim() : "";
                    if(argument.isEmpty()) {
                        throw new JungException("Mark command requires a number. Eg: mark 1");
                    }
                    int index = parseIndexFromCommand(argument);
                    tasks.markTask(index);
                }
                //unmark command
                else if (input.toLowerCase().startsWith("unmark")) {
                    String argument = input.length() > 6 ? input.substring(6).trim() : "";
                    if(argument.isEmpty()) {
                        throw new JungException("Unmark command requires a number. Eg: unmark 1");
                    }
                    int index = parseIndexFromCommand(argument);
                    tasks.unmarkTask(index);
                }
                //todo command
                else if (input.toLowerCase().startsWith("todo")) {
                    String desc = input.length() > 4 ? input.substring(4).trim() : "";
                    if (desc.isEmpty()) {
                        throw new JungException("The description of a todo cannot be empty. Try: todo [description]");
                    }
                    tasks.addTask(new ToDo(desc));
                }
                //deadline command
                else if (input.toLowerCase().startsWith("deadline")) {
                    int byIndex = input.indexOf("/by");
                    if (byIndex == -1) {
                        throw new JungException("Deadline task requires a '/by' date/time. Format: deadline [desc] /by [datetime]");
                    }
                    String desc = input.substring(8, byIndex).trim();
                    String by = input.substring(byIndex + 3).trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        throw new JungException("Deadline description or date/time cannot be empty.");
                    }
                    tasks.addTask(new Deadline(desc, by));
                }
                //event command
                else if (input.toLowerCase().startsWith("event")) {
                    int fromIndex = input.indexOf("/from");
                    int toIndex = input.indexOf("/to");
                    if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                        throw new JungException("Event task requires both '/from' and '/to' date/time. Format: event [desc] /from [start] /to [end]");
                    }
                    String desc = input.substring(5, fromIndex).trim();
                    String from = input.substring(fromIndex + 5, toIndex).trim();
                    String to = input.substring(toIndex + 3).trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new JungException("Event description, start, and end date/time cannot be empty.");
                    }
                    tasks.addTask(new Event(desc, from, to));
                } else {
                    throw new JungException("Sorry, I don't recognise that command. Please try again.");
                }
            } catch (JungException e) {
                System.out.println("Oops! " + e.getMessage());
                System.out.println();
            }

        }
    }

    private static int parseIndexFromCommand(String numberStr) throws JungException {
        try {
            int index = Integer.parseInt(numberStr) - 1;
            if (index < 0) {
                throw new JungException("Task number must be a positive integer.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JungException("Invalid number format for task number.");
        }
    }
}


