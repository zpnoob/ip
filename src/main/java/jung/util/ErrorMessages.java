package jung.util;

/**
 * Centralized error messages for consistent user communication.
 */
public class ErrorMessages {
    public static final String EMPTY_INPUT = "Eh hello, you never say anything leh! Type something lah!";
    public static final String EMPTY_TODO_DESCRIPTION = "Aiyo, your todo description missing leh! " +
            "Try: todo [what you want to do]";
    public static final String INVALID_DATE_FORMAT = "Wah lau, your date format wrong lah! " +
            "Please use d/M/yyyy HHmm (like 15/3/2024 1430). Don't anyhow type!";
    public static final String MISSING_DEADLINE_DATE = "Eh your deadline missing the '/by' part! " +
            "Format should be: deadline [what to do] /by [when]";
    public static final String EMPTY_DEADLINE_FIELDS = "Alamak, your deadline description or date empty leh! " +
            "Fill in properly can or not?";
    public static final String MISSING_EVENT_DATES = "Aiya, your event missing '/from' and '/to' timing! " +
            "How I know when your event start and end?";
    public static final String EMPTY_EVENT_FIELDS = "Your event details incomplete lah! " +
            "Need description, start time AND end time!";
    public static final String MISSING_TASK_NUMBER = " command need task number leh! " +
            "Which task you want to modify?";
    public static final String INVALID_TASK_NUMBER = "Eh that's not a proper number lah! " +
            "Give me real task number can?";
    public static final String MISSING_FIND_KEYWORD = "Find what sia? You never say what to search for!";
    public static final String UNKNOWN_COMMAND = "Ha? I don't understand what you saying leh. " +
            "Type 'list' to see your tasks or try other commands!";
    public static final String INVALID_TASK_INDEX = "That task number doesn't exist lah! " +
            "Check your list first, then try again.";
    public static final String NO_COMMAND_TO_UNDO = "Cannot undo anything leh, you never do anything yet!";
    public static final String NO_TASKS_TO_REMOVE = "Eh no tasks to remove lah! Your list empty already.";
    public static final String UNKNOWN_ACTION = "Alamak, don't know this action: ";
    public static final String UNKNOWN_UNDO_ACTION = "Wah, cannot undo this kind of action leh!";


    private ErrorMessages() {
        // Utility class - prevent instantiation
    }
}
