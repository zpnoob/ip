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

    private static final String[] ADD_RESPONSES = {
            "Aiya okay lah, I help you add this task:",
            "Haiz, another one ah? Fine lor, added:",
            "Wah, you very productive today hor? Added:",
            "Sian... okay okay, I add for you:",
            "Bo bian, since you ask nicely, added:"
    };
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
    /**
     * Formats a Singaporean-style response message for successful task addition.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks after addition
     * @return Formatted success message with local flair
     */
    private String formatAddTaskResponse(Task task, int totalTasks) {
        String response = getRandomResponse();
        String taskCountMessage = getTaskCountMessage(totalTasks);

        return String.format("%s\n  %s\n%s", response, task, taskCountMessage);
    }

    /**
     * Gets a random Singaporean response for task addition.
     */
    private String getRandomResponse() {
        int randomIndex = (int) (Math.random() * ADD_RESPONSES.length);
        return ADD_RESPONSES[randomIndex];
    }

    /**
     * Gets an appropriate task count message based on the number of tasks.
     */
    private String getTaskCountMessage(int totalTasks) {
        if (totalTasks == 1) {
            return "Now you got 1 task only. Not bad ah, still manageable.";
        } else if (totalTasks <= 5) {
            return String.format("Now you got %d tasks. Still okay lah.", totalTasks);
        } else if (totalTasks <= 10) {
            return String.format("Wah, now you got %d tasks liao. Getting busy hor?", totalTasks);
        } else {
            return String.format("Aiyo, %d tasks already?! You very busy person ah! 😅", totalTasks);
        }
    }
}