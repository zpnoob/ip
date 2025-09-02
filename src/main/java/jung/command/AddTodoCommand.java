package jung.command;

import java.io.IOException;

import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import jung.task.ToDo;

/**
 * Command to add a ToDo task to the task list.
 */
public class AddTodoCommand extends Command {

    private String description;

    /**
     * Creates an AddTodoCommand with the ToDo task description.
     *
     * @param description Description of the ToDo task.
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the add todo command by creating and adding a ToDo task.
     *
     * @param tasks   TaskList to add to.
     * @param ui      User interface for showing messages.
     * @param storage Storage to save the task list.
     * @return
     * @throws IOException If storage save fails.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task newTask = new ToDo(description);
        tasks.addTask(newTask);
        ui.showAddTask(newTask, tasks.size());
        return null;
    }
}
