package jung.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task that has a description and a by-date/time.
 */
public class Deadline extends Task {
    private LocalDateTime by; //field determining when to complete task by
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    /**
     * Creates a Deadline task with the given description and deadline date/time.
     *
     * @param description Description of the task.
     * @param by Deadline date/time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description,  'D');
        this.by = by;
    }

    public LocalDateTime getBy() {
        return this.by;
    }

    /**
     * Returns a string representation of the deadline task suitable for user display.
     *
     * @return Readable string of the task.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a string suitable for saving this task to file.
     *
     * @return Formatted string for file storage.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | "+ getDescription() + " | " +
                by.format(INPUT_FORMAT);
    }
}
