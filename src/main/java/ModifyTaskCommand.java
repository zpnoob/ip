import java.io.IOException;

public class ModifyTaskCommand extends Command {

    private String action; // mark, unmark or delete
    private int index;

    public ModifyTaskCommand(String action, int index) {
        this.action = action;
        this.index = index;
    }

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

