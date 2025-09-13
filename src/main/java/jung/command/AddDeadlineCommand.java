package jung.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import jung.exceptions.JungException;
import jung.task.Deadline;
import jung.task.Task;
import jung.util.DateFormats;
import jung.util.ErrorMessages;

/**
 * Command to add a deadline task with a specific due date and time.
 * This helps users track tasks that must be completed by a certain deadline.
 */
public class AddDeadlineCommand extends AddTaskCommand {

    private final String taskDescription;
    private final String deadlineTimeString;

    /**
     * Creates a command to add a deadline task.
     *
     * @param taskDescription What needs to be accomplished
     * @param deadlineTimeString When it must be completed (in d/M/yyyy HHmm format)
     */
    public AddDeadlineCommand(String taskDescription, String deadlineTimeString) {
        this.taskDescription = taskDescription;
        this.deadlineTimeString = deadlineTimeString;
    }

    /**
     * Creates a new Deadline task by parsing the deadline time string.
     *
     * @return A new Deadline task instance
     * @throws JungException If the deadline time format is invalid
     */
    @Override
    protected Task createTask() throws JungException {
        try {
            LocalDateTime deadlineTime = LocalDateTime.parse(deadlineTimeString, DateFormats.INPUT_FORMAT);
            return new Deadline(taskDescription, deadlineTime);
        } catch (DateTimeParseException e) {
            throw new JungException(ErrorMessages.INVALID_DATE_FORMAT);
        }
    }
}