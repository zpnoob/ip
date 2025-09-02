package jung.gui;

import java.io.IOException;
import java.util.ArrayList;

import jung.exceptions.JungException;
import jung.command.Command;
import jung.parser.Parser;
import jung.storage.Storage;
import jung.storage.TaskList;


public class Jung {

    private static TaskList taskList;
    private static Storage storage;
    private static boolean initialized = false;

    public static void main(String[] args) throws IOException {
        Ui ui = new Ui();
        //Ui() will scan inputs instead
        storage = new Storage("data/jung.txt");

        try {
            taskList = new TaskList(storage.load(), storage);
            // Load what has already been in storage
        } catch (IOException e) {
            System.out.println("Failed to load tasks, starting with an empty list.");
            taskList = new TaskList(new ArrayList<>(), storage);
            // Initialises an empty list if no data/jung.txt in storage
        }

        System.out.println("Hello! I'm Jung.");
        System.out.println("I don't really want to help but bopes, " +
                "what can I do for you today?\n");

        boolean exit = false;

        while (!exit) {
            String input = ui.readCommand();
            try {
                Command command = Parser.parse(input);
                String result = command.execute(taskList, null, storage);
                System.out.println(result);
                exit = command.isExit();
            } catch (JungException | IOException e) {
                System.out.println("Oops! " + e.getMessage());
            }
        }
    }

    public static String initialize() throws IOException {
        if (initialized) return "";

        storage = new Storage("data/jung.txt");
        try {
            taskList = new TaskList(storage.load(), storage);
        } catch (IOException e) {
            taskList = new TaskList(new ArrayList<>(), storage);
            initialized = true;
            return "Failed to load tasks, starting with an empty lists.";
        }
        initialized = true;
        return "Hello! I'm Jung.\nI don't really want to help but bopes, " +
                "what can I do for you today?";
    }

    public static String getResponse(String input) {
        try {
            if (!initialized) {
                initialize();
            }
            Command command = Parser.parse(input);
            return command.execute(taskList, null, storage);
        } catch (JungException | IOException e) {
            return "Oops! " + e.getMessage();
        }
    }

    public static TaskList getTaskList() {
        return taskList;
    }

    public static Storage getStorage() {
        return storage;
    }

    public String getWelcomeMessage() {
        return "Hello! I'm Jung.\nI don't really want to help but bopes, what can I do for you today?";
    }


}





