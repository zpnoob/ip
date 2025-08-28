package jung.command;

import jung.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks.getTasks());
    }
}