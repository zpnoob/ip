package jung.command;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.storage.UndoableAction;

public class UndoCommand extends Command{
    /**
     * Executes the undo command by reverting the most recent undoable operation.
     *
     * @param tasks   TaskList to perform undo operation on.
     * @param ui      User interface for displaying messages.
     * @param storage Storage to save changes after undo.
     * @return Result message indicating what was undone.
     * @throws JungException If no command can be undone.
     * @throws IOException   If storage save fails.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JungException, IOException {
        UndoableAction lastAction = tasks.getLastAction();
        if (lastAction == null) {
            throw new JungException("No command to undo.");
        }

        String result = lastAction.executeUndo(tasks);
        tasks.clearLastAction(); // Clear the undo action after using it
        storage.save(tasks.getTasks()); // Save the changes
        return result;
    }
}
