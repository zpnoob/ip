package jung.command;

import java.io.IOException;

import jung.JungException;
import jung.Ui;
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
     * @param tasks TaskList containing tasks.
     * @param ui User interface for user messages.
     * @param storage Storage to save changes.
     * @throws JungException If invalid action or index.
     * @throws IOException If storage save fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JungException, IOException {
        switch (action) {
        case "mark":
            Task marked = tasks.markTask(index);
            ui.showMark(marked);
            break;

        case "unmark":
            Task unmarked = tasks.unmarkTask(index);
            ui.showUnmark(unmarked);
            break;

        case "delete":
            Task removed = tasks.deleteTask(index);
            ui.showDeleteTask(removed, tasks.size());
            break;

        default:
            throw new JungException("Unknown action: " + action);
        }
    }
}

