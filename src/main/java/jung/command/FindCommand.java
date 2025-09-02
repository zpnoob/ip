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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws
            IOException, JungException {
        ArrayList<Task> matchedTasks = tasks.findTasksByKeyword(keyword);
        ui.showFind(matchedTasks);
    }

}
