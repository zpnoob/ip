package jung.util;

/**
 * Centralized error messages for consistent user communication.
 */
public class ErrorMessages {
    public static final String EMPTY_INPUT = "Input cannot be empty";
    public static final String EMPTY_TODO_DESCRIPTION = "The description of a todo cannot be empty. Try: todo [description]";
    public static final String INVALID_DATE_FORMAT = "Invalid date/time format. Please use d/M/yyyy HHmm (e.g., 15/3/2024 1430)";
    public static final String MISSING_DEADLINE_DATE = "Deadline task requires a '/by' date/time. Format: deadline [desc] /by [datetime]";
    public static final String EMPTY_DEADLINE_FIELDS = "Deadline description or date/time cannot be empty.";
    public static final String MISSING_EVENT_DATES = "Event task requires both '/from' and '/to' date/time.";
    public static final String EMPTY_EVENT_FIELDS = "Event description, start, and end date/time cannot be empty.";
    public static final String MISSING_TASK_NUMBER = " command requires a task number.";
    public static final String INVALID_TASK_NUMBER = "Invalid task number format.";
    public static final String MISSING_FIND_KEYWORD = "The find command requires a keyword to search.";
    public static final String UNKNOWN_COMMAND = "Sorry, I don't recognise that command.";
    public static final String INVALID_TASK_INDEX = "Invalid task number, please enter a valid number.";
    public static final String NO_COMMAND_TO_UNDO = "No command to undo.";
    public static final String NO_TASKS_TO_REMOVE = "No tasks to remove.";
    public static final String UNKNOWN_ACTION = "Unknown action: ";
    public static final String UNKNOWN_UNDO_ACTION = "Unknown action type for undo.";

    private ErrorMessages() {
        // Utility class - prevent instantiation
    }
}
