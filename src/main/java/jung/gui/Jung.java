package jung.gui;

import java.io.IOException;
import java.util.ArrayList;
import jung.exceptions.JungException;
import jung.command.Command;
import jung.parser.Parser;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.util.CommandResult;

/**
 * Core application class that manages task operations and coordinates between
 * the parser, storage, and task list components.
 */
public class Jung {

    private static final String WELCOME_MESSAGE = "Wah, hello! I'm Jung.\n" +
            "Aiyo, I don't really want to help but... sian, what you want me to do for you today?\n" +
            "Just don't give me too much work can or not? 😤";
    private static final String STORAGE_PATH = "data/jung.txt";
    private static final String LOAD_FAILURE_MESSAGE = "Wah lau eh, cannot load your tasks leh. " +
            "Never mind, start fresh lor!";

    private TaskList taskList;
    private Storage storage;
    private boolean initialized = false;

    /**
     * Initializes the Jung application with storage and task list.
     *
     * @return Initialization message (welcome or error message)
     * @throws IOException If storage setup fails
     */
    public String initialize() throws IOException {
        if (initialized) {
            return "";
        }

        setupStorage();
        String initializationMessage = setupTaskList();
        initialized = true;

        return initializationMessage;
    }

    /**
     * Processes a user command and returns the result.
     * Automatically initializes if not already done.
     *
     * @param input User command string
     * @return Result of command execution
     * @throws JungException If command parsing or execution fails
     * @throws IOException   If storage operations fail
     */
    public CommandResult processCommand(String input) throws JungException, IOException {
        ensureInitialized();

        Command command = Parser.parse(input);
        return command.execute(taskList, null, storage);
    }

    /**
     * Gets the welcome message for new users.
     *
     * @return Welcome message string
     */
    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }

    /**
     * Gets the current task list (for GUI access).
     *
     * @return The task list instance
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Gets the storage instance (for GUI access).
     *
     * @return The storage instance
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Ensures the application is initialized before processing commands.
     */
    private void ensureInitialized() throws IOException {
        if (!initialized) {
            initialize();
        }
    }

    /**
     * Sets up the storage system.
     */
    private void setupStorage() throws IOException {
        storage = new Storage(STORAGE_PATH);
    }

    /**
     * Sets up the task list by loading existing tasks or creating an empty list.
     *
     * @return Initialization message (empty string on success, error message on failure)
     */
    private String setupTaskList() {
        try {
            ArrayList<jung.task.Task> loadedTasks = storage.load();
            taskList = new TaskList(loadedTasks, storage);
            return ""; // Success - no message needed
        } catch (IOException e) {
            taskList = new TaskList(new ArrayList<>(), storage);
            return LOAD_FAILURE_MESSAGE;
        }
    }
}





