package jung.command;

import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;

/**
 * Command to exit the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command, prints farewell message.
     *
     * @param tasks   Not used in ExitCommand.
     * @param ui      Not used in ExitCommand.
     * @param storage Not used in ExitCommand.
     * @return
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye. I hope I never see you again..";
    }

    /**
     * Returns true to indicate this command exits the application.
     *
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}

