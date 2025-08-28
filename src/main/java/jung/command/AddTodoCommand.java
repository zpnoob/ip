package jung.command;

import java.io.IOException;

import jung.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import jung.task.ToDo;

public class AddTodoCommand extends Command {

    private String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task newTask = new ToDo(description);
        tasks.addTask(newTask);
        ui.showAddTask(newTask, tasks.size());
    }
}
