package jung.command;

import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import jung.util.CommandResult;
import java.util.ArrayList;

/**
 * Command to display all tasks currently stored in the user's task list.
 * Provides a numbered list of all tasks with their current status.
 */
public class ListCommand extends Command {

    private static final String[] EMPTY_LIST_MESSAGES = {
            "Eh your task list empty leh! Very free ah you?",
            "Wah, no tasks at all! Shiok life you living!",
            "Your list got nothing inside. Add some tasks lah!",
            "Empty list sia. Time to get busy!"
    };

    private static final String[] LIST_HEADERS = {
            "Okay here are your tasks lah:",
            "Your task list coming up:",
            "See, these are all the things you need to do:",
            "Here's your to-do list. Jiayou!"
    };

    /**
     * Executes the list command to show all tasks to the user.
     *
     * @param tasks TaskList containing all user tasks
     * @param ui User interface for display (not used directly)
     * @param storage Not used in list operations
     * @return Result containing formatted task list or empty message
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> allTasks = tasks.getTasks();

        if (allTasks.isEmpty()) {
            String message = getRandomMessage(EMPTY_LIST_MESSAGES);
            return new CommandResult(message);
        }

        String formattedList = formatTaskList(allTasks);
        return new CommandResult(formattedList);
    }

    /**
     * Formats the task list into a numbered, user-friendly display.
     *
     * @param tasks List of tasks to format
     * @return Formatted string with numbered task list
     */
    private String formatTaskList(ArrayList<Task> tasks) {
        StringBuilder listBuilder = new StringBuilder(getRandomMessage(LIST_HEADERS));
        listBuilder.append("\n");

        for (int i = 0; i < tasks.size(); i++) {
            int taskNumber = i + 1;
            listBuilder.append(taskNumber).append(". ").append(tasks.get(i)).append("\n");
        }

        return listBuilder.toString();
    }

    private String getRandomMessage(String[] messages) {
        int randomIndex = (int) (Math.random() * messages.length);
        return messages[randomIndex];
    }
}
