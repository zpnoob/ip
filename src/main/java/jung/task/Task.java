package jung.task;

/**
 * Abstract base class representing a task in the Jung task management system.
 * All tasks have a description, completion status, and type identifier.
 */
public abstract class Task {

    private static final String DONE_ICON = "X";
    private static final String NOT_DONE_ICON = " ";

    private final String description;
    private final char taskSymbol;
    private boolean isDone;

    /**
     * Creates a new task with the given description and type symbol.
     * Tasks start in an incomplete state by default.
     *
     * @param description What the task involves
     * @param taskSymbol Character representing the task type (T/D/E)
     */
    public Task(String description, char taskSymbol) {
        this.description = description;
        this.taskSymbol = taskSymbol;
        this.isDone = false;
    }

    /**
     * Gets the visual indicator for this task's completion status.
     *
     * @return "X" if done, " " if not done
     */
    public String getStatusIcon() {
        return isDone ? DONE_ICON : NOT_DONE_ICON;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Checks if this task has been completed.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Gets the description of what this task involves.
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the character symbol representing this task's type.
     *
     * @return The task type symbol (T for Todo, D for Deadline, E for Event)
     */
    public char getTaskSymbol() {
        return taskSymbol;
    }

    /**
     * Converts this task to a string format suitable for file storage.
     * Each task type implements its own storage format.
     *
     * @return String representation for file persistence
     */
    public abstract String toFileString();

    /**
     * Returns a user-friendly string representation of this task.
     * Shows the task type, completion status, and description.
     *
     * @return Formatted task string for display
     */
    @Override
    public String toString() {
        return String.format("[%c][%s] %s", taskSymbol, getStatusIcon(), description);
    }
}