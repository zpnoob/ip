package jung.command;

import jung.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;

/**
 * Command to list all tasks currently in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command to show all tasks.
     *
     * @param tasks TaskList containing tasks to show.
     * @param ui User interface for displaying the task list.
     * @param storage Not used in ListCommand.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks.getTasks());
    }
}