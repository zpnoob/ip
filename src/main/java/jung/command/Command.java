package jung.command;

import java.io.IOException;

import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws JungException, IOException;

    public boolean isExit() {
        return false;
    }
}