package jung.command;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;

/**
 * Command to modify tasks by marking, unmarking, or deleting them.
 */
public class ModifyTaskCommand extends Command {

    private String action; // mark, unmark or delete
    private int index;

    /**
     * Constructs ModifyTaskCommand with action and task index.
     *
     * @param action The action to perform: "mark", "unmark", or "delete".
     * @param index The zero-based index of the task.
     */
    public ModifyTaskCommand(String action, int index) {
        this.action = action;
        this.index = index;
    }

    /**
     * Executes the modify command by performing the specified action on the task.
     *
     * @param tasks   TaskList containing tasks.
     * @param ui      User interface for user messages.
     * @param storage Storage to save changes.
     * @return result message
     * @throws JungException If invalid action or index.
     * @throws IOException   If storage save fails.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JungException, IOException {
        assert action.equals("mark") || action.equals("unmark") || action.equals("delete") :
                "Action must be mark, unmark, or delete";
        assert index >= 0 : "Index should not be negative";

        switch (action) {
        case "mark":
            return formatResult("marked as done", tasks.markTask(index));
        case "unmark":
            return formatResult("marked as not done yet", tasks.unmarkTask(index));
        case "delete":
            Task deletedTask = tasks.deleteTask(index);
            return formatResult("removed", deletedTask) + "\nNow you have "
                    + tasks.size() + " tasks in the list.";
        default:
            throw new JungException("Unknown action: " + action);
        }
    }

    public String formatResult (String actionDescription, Task task) {
        return "Lame. I've " + actionDescription + " this task:\n " + task;
    }
}

