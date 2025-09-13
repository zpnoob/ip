package jung.command;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import jung.util.CommandResult;

/**
 * Abstract base class for commands that add tasks to the task list.
 * Provides common functionality for task addition and response formatting.
 */
public abstract class AddTaskCommand extends Command {

    /**
     * Creates the specific task instance based on the command parameters.
     * Subclasses implement this to create Todo, Deadline, or Event tasks.
     *
     * @return The task to be added to the list
     * @throws JungException If task creation fails due to invalid parameters
     */
    protected abstract Task createTask() throws JungException;

    /**
     * Executes the task addition by creating the task and adding it to the list.
     *
     * @param tasks The task list to add to
     * @param ui User interface (not used in add operations)
     * @param storage Storage system for persistence
     * @return Result containing success message
     * @throws JungException If task creation or validation fails
     * @throws IOException If storage operations fail
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage)
            throws JungException, IOException {
        Task newTask = createTask();
        tasks.addTask(newTask);
        String message = formatAddTaskResponse(newTask, tasks.size());
        return new CommandResult(message);
    }

    /**
     * Formats a consistent response message for successful task addition.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks after addition
     * @return Formatted success message
     */
    private String formatAddTaskResponse(Task task, int totalTasks) {
        return String.format("Okay. I've added this task:\n  %s\nYou now have %d tasks in the list.",
                task, totalTasks);
    }
}