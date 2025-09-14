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
        MARK("sibei good, marked as done", new String[]{
                "Steady lah! This task is done:",
                "Wah, finally finished ah? Good job:",
                "Nice one! Completed this task:",
                "Power lah! This one settled already:"
        }),
        UNMARK("aiya, marked as not done yet", new String[]{
                "Haiz, back to not done ah:",
                "Alamak, undoing this task:",
                "Aiya, this one not finished yet:",
                "Sian, back to incomplete:"
        }),
        DELETE("sayonara, removed", new String[]{
                "Okay lor, deleted this task:",
                "Bye bye task! Removed:",
                "Gone already! Deleted:",
                "Poof! This task disappeared:"
        });

        private final String description;
        private final String[] responses;

        Action(String description, String[] responses) {
            this.description = description;
            this.responses = responses;
        }

        public String getDescription() {
            return description;
        }

        public String getRandomResponse() {
            int randomIndex = (int) (Math.random() * responses.length);
            return responses[randomIndex];
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
            return new CommandResult(formatResult(action.getRandomResponse(), markedTask));

        case UNMARK:
            Task unmarkedTask = tasks.unmarkTask(taskIndex);
            return new CommandResult(formatResult(action.getRandomResponse(), unmarkedTask));

        case DELETE:
            Task deletedTask = tasks.deleteTask(taskIndex);
            String deleteMessage = formatResult(action.getRandomResponse(), deletedTask)
                    + "\n" + getTaskCountMessage(tasks.size());
            return new CommandResult(deleteMessage);
        default:
            throw new JungException(ErrorMessages.UNKNOWN_ACTION + action);
        }
    }

    /**
     * Formats a consistent result message for task modifications.
     *
     * @param actionResponse Description of the action performed
     * @param task The task that was modified
     * @return Formatted result message
     */
    private String formatResult(String actionResponse, Task task) {
        return actionResponse + "\n  " + task;
    }

    /**
     * Gets an appropriate message about remaining task count after deletion.
     */
    private String getTaskCountMessage(int remainingTasks) {
        if (remainingTasks == 0) {
            return "Wah, no more tasks! You free bird now! 🐦";
        } else if (remainingTasks == 1) {
            return "Now you got 1 task left only. Almost done lah!";
        } else if (remainingTasks <= 5) {
            return String.format("Still got %d tasks left. Can finish one!", remainingTasks);
        } else {
            return String.format("Wah, still got %d more tasks. Jiayou! 💪", remainingTasks);
        }
    }
}

