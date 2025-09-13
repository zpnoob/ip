package jung.task;

import java.time.LocalDateTime;
import jung.util.DateFormats;
import jung.util.TaskType;

/**
 * Represents a task with a specific deadline for completion.
 * These tasks help users track what must be done by a certain date and time.
 */
public class Deadline extends Task {

    private final LocalDateTime deadlineTime;

    /**
     * Creates a new deadline task with description and due date/time.
     *
     * @param description What needs to be accomplished
     * @param deadlineTime When the task must be completed by
     */
    public Deadline(String description, LocalDateTime deadlineTime) {
        super(description, TaskType.DEADLINE.getSymbol());
        this.deadlineTime = deadlineTime;
    }

    /**
     * Gets the deadline date and time for this task.
     *
     * @return When this task is due
     */
    public LocalDateTime getDeadlineTime() {
        return deadlineTime;
    }

    /**
     * Returns a user-friendly string showing the task and its deadline.
     *
     * @return Formatted string with deadline information
     */
    @Override
    public String toString() {
        String formattedDeadline = deadlineTime.format(DateFormats.OUTPUT_FORMAT);
        return super.toString() + " (by: " + formattedDeadline + ")";
    }

    /**
     * Converts this deadline task to file storage format.
     * Format: "D | [1|0] | description | deadline_date_time"
     *
     * @return String representation for file storage
     */
    @Override
    public String toFileString() {
        String completionFlag = isDone() ? "1" : "0";
        String formattedDeadline = deadlineTime.format(DateFormats.INPUT_FORMAT);
        return String.format("D | %s | %s | %s", completionFlag, getDescription(), formattedDeadline);
    }
}