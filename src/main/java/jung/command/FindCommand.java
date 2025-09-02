package jung.command;

import java.io.IOException;
import java.util.ArrayList;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;

/**
 * Command to find tasks containing a keyword.
 */
public class FindCommand extends Command{

    private String keyword;

    public FindCommand (String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws
            IOException, JungException {
        ArrayList<Task> matchedTasks = tasks.findTasksByKeyword(keyword);
        if (matchedTasks.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchedTasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(matchedTasks.get(i)).append("\n");
        }
        return sb.toString();
    }

}
