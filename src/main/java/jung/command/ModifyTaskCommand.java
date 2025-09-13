package jung.command;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import jung.util.CommandResult;
import jung.util.ErrorMessages;

/**
 * Command to modify existing tasks by marking them as done/undone or deleting them.
 * Supports mark, unmark, and delete operations on tasks by index.
 */
public class ModifyTaskCommand extends Command {

    /**
     * Enumeration of supported task modification operations.
     */
    public enum Action {
        MARK("marked as done"),
        UNMARK("marked as not done yet"),
        DELETE("removed");

        private final String description;

        Action(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private final Action action;
    private final int taskIndex;

    /**
     * Creates a command to modify a task with the specified action.
     *
     * @param action The modification operation to perform
     * @param taskIndex The zero-based index of the task to modify
     */
    public ModifyTaskCommand(Action action, int taskIndex) {
        this.action = action;
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the modification operation on the specified task.
     *
     * @param tasks TaskList containing the task to modify
     * @param ui User interface for messages (not used directly)
     * @param storage Storage system for persistence
     * @return Result containing operation success message
     * @throws JungException If task index is invalid or operation fails
     * @throws IOException If storage operations fail
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage)
            throws JungException, IOException {

        switch (action) {
        case MARK:
            Task markedTask = tasks.markTask(taskIndex);
            return new CommandResult(formatResult(action.getDescription(), markedTask));

        case UNMARK:
            Task unmarkedTask = tasks.unmarkTask(taskIndex);
            return new CommandResult(formatResult(action.getDescription(), unmarkedTask));

        case DELETE:
            Task deletedTask = tasks.deleteTask(taskIndex);
            String deleteMessage = formatResult(action.getDescription(), deletedTask)
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
            return new CommandResult(deleteMessage);

        default:
            throw new JungException(ErrorMessages.UNKNOWN_ACTION + action);
        }
    }

    /**
     * Formats a consistent result message for task modifications.
     *
     * @param actionDescription Description of the action performed
     * @param task The task that was modified
     * @return Formatted result message
     */
    private String formatResult(String actionDescription, Task task) {
        return "Lame. I've " + actionDescription + " this task:\n " + task;
    }
}

