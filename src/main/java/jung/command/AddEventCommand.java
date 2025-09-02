package jung.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jung.exceptions.JungException;
import jung.gui.Ui;
import jung.storage.Storage;
import jung.storage.TaskList;
import jung.task.Event;
import jung.task.Task;
/**
 * Command to add an event task with from and to date/time.
 */
public class AddEventCommand extends Command {

    private String description;
    private String fromStr;
    private String toStr;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Creates AddEventCommand with description, start, and end date/time.
     *
     * @param description Description of event.
     * @param fromStr Start date/time string.
     * @param toStr End date/time string.
     */
    public AddEventCommand(String description, String fromStr, String toStr) {
        this.description = description;
        this.fromStr = fromStr;
        this.toStr = toStr;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException, JungException {
        try {
            LocalDateTime from = LocalDateTime.parse(fromStr, formatter);
            LocalDateTime to = LocalDateTime.parse(toStr, formatter);
            Task newTask = new Event(description, from, to);
            tasks.addTask(newTask);
            ui.showAddTask(newTask, tasks.size());
        } catch (DateTimeParseException e) {
            throw new JungException("Invalid date/time format. Please use d/M/yyyy HHmm.");
        }
        return null;
    }
}
