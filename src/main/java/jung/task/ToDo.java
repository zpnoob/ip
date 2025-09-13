package jung.task;

import jung.util.TaskType;

/**
 * Represents a simple todo task without any time constraints.
 * These are basic tasks that need to be completed but have no specific deadline.
 */
public class ToDo extends Task {

    /**
     * Creates a new todo task with the given description.
     *
     * @param description What needs to be done
     */
    public ToDo(String description) {
        super(description, TaskType.TODO.getSymbol());
    }

    /**
     * Converts this todo task to file storage format.
     * Format: "T | [1|0] | description"
     *
     * @return String representation for file storage
     */
    @Override
    public String toFileString() {
        String completionFlag = isDone() ? "1" : "0";
        return String.format("T | %s | %s", completionFlag, getDescription());
    }
}