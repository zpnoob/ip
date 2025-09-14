package jung.command;

import java.util.ArrayList;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Task;
import jung.util.CommandResult;

/**
 * Command to search for tasks containing a specific keyword.
 * Performs case-insensitive matching against task descriptions.
 */
public class FindCommand extends Command {

    private static final String[] NO_MATCHES_MESSAGES = {
            "Eh cannot find anything matching leh! Try different keyword?",
            "No such thing in your list lah! Sure or not you got this task?",
            "Cannot find anything sia. Maybe you spell wrong?",
            "Your search got no results. Try again with different words!"
    };

    private static final String[] MATCHES_HEADERS = {
            "Found some tasks that match your search:",
            "Here are the tasks I found for you:",
            "Wah got some matches leh:",
            "These tasks match what you looking for:"
    };
    private final String searchKeyword;

    /**
     * Creates a command to find tasks containing the given keyword.
     *
     * @param keyword The text to search for in task descriptions (case-insensitive)
     */
    public FindCommand(String keyword) {
        this.searchKeyword = keyword.toLowerCase();
    }

    /**
     * Searches for tasks containing the keyword and formats the results.
     *
     * @param tasks TaskList to search through
     * @param ui User interface (not used directly)
     * @param storage Not used in find operations
     * @return Result containing matching tasks or no matches message
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchedTasks = tasks.findTasksByKeyword(searchKeyword);

        if (matchedTasks.isEmpty()) {
            String message = getRandomMessage(NO_MATCHES_MESSAGES);
            return new CommandResult(message);
        }

        String formattedResults = formatSearchResults(matchedTasks);
        return new CommandResult(formattedResults);
    }

    /**
     * Formats the search results into a numbered list.
     *
     * @param matchedTasks List of tasks that contain the search keyword
     * @return Formatted string with numbered results
     */
    private String formatSearchResults(ArrayList<Task> matchedTasks) {
        StringBuilder resultsBuilder = new StringBuilder(getRandomMessage(MATCHES_HEADERS));
        resultsBuilder.append("\n");

        for (int i = 0; i < matchedTasks.size(); i++) {
            int resultNumber = i + 1;
            resultsBuilder.append(resultNumber).append(". ").append(matchedTasks.get(i)).append("\n");
        }

        return resultsBuilder.toString();
    }

    private String getRandomMessage(String[] messages) {
        int randomIndex = (int) (Math.random() * messages.length);
        return messages[randomIndex];
    }
}