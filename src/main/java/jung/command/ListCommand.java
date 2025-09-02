package jung.command;

import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import java.util.ArrayList;

/**
 * Command to list all tasks currently in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command to show all tasks.
     *
     * @param tasks   TaskList containing tasks to show.
     * @param ui      User interface for displaying the task list.
     * @param storage Not used in ListCommand.
     * @return
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> list = tasks.getTasks();
        if (list.isEmpty()) {
            return "Your task list is currently empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1)).append(". ").append(list.get(i)).append("\n");
        }
        return sb.toString();

    }
}