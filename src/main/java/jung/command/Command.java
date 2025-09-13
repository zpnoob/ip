package jung.command;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.util.CommandResult;

/**
 * Abstract base class for all user commands in the Jung task manager.
 * Provides a template for command execution and application flow control.
 */
public abstract class Command {

    /**
     * Executes this command with the given application context.
     *
     * @param tasks The task list to operate on
     * @param ui The user interface for interactions
     * @param storage The storage system for persistence
     * @return The result of command execution
     * @throws JungException If the command cannot be executed due to business logic violations
     * @throws IOException If storage operations fail
     */
    public abstract CommandResult execute(TaskList tasks, Ui ui, Storage storage) throws JungException, IOException;

    public boolean isExit() {
        return false;
    }
}