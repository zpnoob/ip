package jung.command;

import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.util.CommandResult;

/**
 * Command to gracefully exit the Jung application.
 * Displays a farewell message before terminating.
 */
public class ExitCommand extends Command {

    private static final String FAREWELL_MESSAGE = "Bye bye! Don't miss me too much ah!";

    /**
     * Executes the exit command by preparing the farewell message.
     * The actual application termination is handled by the UI layer.
     *
     * @param tasks Not used in exit operations
     * @param ui Not used in exit operations
     * @param storage Not used in exit operations
     * @return Result indicating the application should exit
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        return new CommandResult(FAREWELL_MESSAGE, true);
    }

    /**
     * Indicates that this command terminates the application.
     *
     * @return true to signal application exit
     */
    @Override
    public boolean isExit() {
        return true;
    }
}

