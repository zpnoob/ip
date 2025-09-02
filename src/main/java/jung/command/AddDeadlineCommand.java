package jung.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Deadline;
import jung.task.Task;

/**
 * Adds a deadline task to the task list.
 * Parses the date/time string and creates a Deadline task.
 */
public class AddDeadlineCommand extends Command {

    private String description;
    private String byStr;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructor for AddDeadlineCommand.
     *
     * @param description Task description.
     * @param byStr Deadline date/time in "d/M/yyyy HHmm" format.
     */
    public AddDeadlineCommand(String description, String byStr) {
        this.description = description;
        this.byStr = byStr;
    }

    /**
     * Executes the add deadline command by creating a Deadline task and adding it.
     *
     * @param tasks   TaskList to add the task into.
     * @param ui      Ui interface for user interactions.
     * @param storage Storage to persist tasks.
     * @return
     * @throws IOException   If saving tasks fails.
     * @throws JungException If date/time parsing fails.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws
            IOException, JungException {
        try {
            LocalDateTime by = LocalDateTime.parse(byStr, formatter);
            Task newTask = new Deadline(description, by);
            tasks.addTask(newTask);
            return "Okay. I've added this task:\n  " + newTask + "\nYou now have "
                    + tasks.size() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            throw new JungException("Invalid date/time format. " +
                    "Please use d/M/yyyy HHmm.");
        }
    }
}

