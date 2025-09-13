package jung.command;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.storage.UndoableAction;
import jung.util.CommandResult;
import jung.util.ErrorMessages;

/**
 * Command to reverse the most recent undoable operation.
 * Provides users with the ability to correct mistakes in task management.
 */
public class UndoCommand extends Command {

    /**
     * Executes the undo operation by reversing the most recent undoable command.
     *
     * @param tasks TaskList to perform undo operation on
     * @param ui User interface for messages (not used directly)
     * @param storage Storage system to persist changes after undo
     * @return Result indicating what operation was undone
     * @throws JungException If no command is available to undo
     * @throws IOException If storage operations fail
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage)
            throws JungException, IOException {

        UndoableAction lastAction = tasks.getLastAction();
        if (lastAction == null) {
            throw new JungException(ErrorMessages.NO_COMMAND_TO_UNDO);
        }

        String undoResult = lastAction.executeUndo(tasks);
        tasks.clearLastAction();
        storage.save(tasks.getTasks());

        return new CommandResult(undoResult);
    }
}