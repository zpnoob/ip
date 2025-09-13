package jung.task;

import java.time.LocalDateTime;
import jung.util.DateFormats;
import jung.util.TaskType;

/**
 * Represents an event task that occurs during a specific time period.
 * These tasks help users track activities with defined start and end times.
 */
public class Event extends Task {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Creates a new event task with description and time period.
     *
     * @param description What the event is about
     * @param startTime When the event begins
     * @param endTime When the event concludes
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(description, TaskType.EVENT.getSymbol());
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets the start date and time of this event.
     *
     * @return When this event begins
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end date and time of this event.
     *
     * @return When this event concludes
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Returns a user-friendly string showing the event and its time period.
     *
     * @return Formatted string with start and end times
     */
    @Override
    public String toString() {
        String formattedStart = startTime.format(DateFormats.OUTPUT_FORMAT);
        String formattedEnd = endTime.format(DateFormats.OUTPUT_FORMAT);
        return super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")";
    }

    /**
     * Converts this event task to file storage format.
     * Format: "E | [1|0] | description | start_date_time | end_date_time"
     *
     * @return String representation for file storage
     */
    @Override
    public String toFileString() {
        String completionFlag = isDone() ? "1" : "0";
        String formattedStart = startTime.format(DateFormats.INPUT_FORMAT);
        String formattedEnd = endTime.format(DateFormats.INPUT_FORMAT);
        return String.format("E | %s | %s | %s | %s", completionFlag, getDescription(), formattedStart, formattedEnd);
    }
}