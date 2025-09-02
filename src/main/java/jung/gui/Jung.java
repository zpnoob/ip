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
    private static Ui ui;
    private static boolean initialized = false;

    public static void main(String[] args) throws IOException {
        ui = new Ui();
        //Ui() will scan inputs instead
        storage = new Storage("data/jung.txt");

        try {
            taskList = new TaskList(storage.load(), storage);
            // Load what has already been in storage
        } catch (IOException e) {
            ui.showLoadingError();
            taskList = new TaskList(new ArrayList<>(), storage);
            // Initialises an empty list if no data/jung.txt in storage
        }

        ui.showWelcome();

        boolean exit = false;

        while (!exit) {
            String input = ui.readCommand();
            try {
                Command command = Parser.parse(input);
                command.execute(taskList, ui, storage);
                exit = command.isExit();
            } catch (JungException | IOException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private static void initialize() throws IOException {
        if (initialized) return;
        ui = new Ui();
        storage = new Storage("data/jung.txt");
        try {
            taskList = new TaskList(storage.load(), storage);
        } catch (IOException e) {
            ui.showLoadingError();
            taskList = new TaskList(new ArrayList<>(), storage);
        }
        initialized = true;
    }

    public static String getResponse(String input) {
        try {
            if (!initialized) {
                initialize();
            }
            Command command = Parser.parse(input);
            String output = command.execute(taskList, ui, storage);
            if (command.isExit()) {
                // Optionally handle app exit if needed
            }
            return output;
        } catch (JungException | IOException e) {
            return e.getMessage();
        }
    }

    public String getWelcomeMessage() {
        return "Hello! I'm Jung.\nI don't really want to help but bopes, what can I do for you today?";
    }
}





