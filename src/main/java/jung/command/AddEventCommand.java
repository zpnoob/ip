package jung.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import jung.exceptions.JungException;
import jung.task.Event;
import jung.task.Task;
import jung.util.DateFormats;
import jung.util.ErrorMessages;

/**
 * Command to add an event task with start and end times.
 * This allows users to track activities that span a specific time period.
 */
public class AddEventCommand extends AddTaskCommand {

    private final String taskDescription;
    private final String startTimeString;
    private final String endTimeString;

    /**
     * Creates a command to add an event task.
     *
     * @param taskDescription What the event is about
     * @param startTimeString When the event begins (in d/M/yyyy HHmm format)
     * @param endTimeString When the event ends (in d/M/yyyy HHmm format)
     */
    public AddEventCommand(String taskDescription, String startTimeString, String endTimeString) {
        this.taskDescription = taskDescription;
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
    }

    /**
     * Creates a new Event task by parsing the start and end time strings.
     *
     * @return A new Event task instance
     * @throws JungException If either time format is invalid
     */
    @Override
    protected Task createTask() throws JungException {
        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeString, DateFormats.INPUT_FORMAT);
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, DateFormats.INPUT_FORMAT);
            // Validate logical date constraints
            if (startTime.isAfter(endTime)) {
                throw new JungException("Aiyo! Start time cannot be later than end time lah!");
            }

            if (startTime.equals(endTime)) {
                throw new JungException("Start time and end time same-same! How can be event?");
            }

            // Optional: Check for reasonable time differences (at least 1 minute)
            if (java.time.Duration.between(startTime, endTime).toMinutes() < 1) {
                throw new JungException("Your event too short lah! Make it at least 1 minute long!");
            }

            return new Event(taskDescription, startTime, endTime);
        } catch (DateTimeParseException e) {
            throw new JungException(ErrorMessages.INVALID_DATE_FORMAT);
        }
    }
}
