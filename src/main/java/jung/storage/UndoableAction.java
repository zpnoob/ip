package jung.storage;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.task.Task;
import jung.util.ErrorMessages;

/**
 * Represents an action that can be reversed, storing all information needed
 * to undo a task list operation.
 */
public class UndoableAction {

    /**
     * Types of operations that can be undone.
     */
    public enum ActionType {
        ADD_TASK,
        DELETE_TASK,
        MARK_TASK,
        UNMARK_TASK
    }

    private final ActionType actionType;
    private final Task task;
    private final int originalIndex;
    private final String actionDescription;

    /**
     * Creates an undoable action for add operations (no index needed).
     *
     * @param actionType Type of operation performed
     * @param task Task that was affected
     * @param actionDescription Human-readable description of what was done
     */
    public UndoableAction(ActionType actionType, Task task, String actionDescription) {
        this(actionType, task, -1, actionDescription);
    }

    /**
     * Creates an undoable action for operations requiring an index.
     *
     * @param actionType Type of operation performed
     * @param task Task that was affected
     * @param originalIndex Original position of the task (for delete operations)
     * @param actionDescription Human-readable description of what was done
     */
    public UndoableAction(ActionType actionType, Task task, int originalIndex, String actionDescription) {
        this.actionType = actionType;
        this.task = task;
        this.originalIndex = originalIndex;
        this.actionDescription = actionDescription;
    }

    /**
     * Executes the undo operation on the specified task list.
     *
     * @param taskList Task list to perform the undo on
     * @return Description of what was undone
     * @throws JungException If the undo operation fails
     * @throws IOException If storage operations fail
     */
    public String executeUndo(TaskList taskList) throws JungException, IOException {
        switch (actionType) {
        case ADD_TASK:
            taskList.removeLastTask();
            return "Undone: " + actionDescription;
        case DELETE_TASK:
            taskList.insertTaskAt(task, originalIndex);
            return "Undone: " + actionDescription;
        case MARK_TASK:
            taskList.unmarkTaskSilently(originalIndex);
            return "Undone: " + actionDescription;
        case UNMARK_TASK:
            taskList.markTaskSilently(originalIndex);
            return "Undone: " + actionDescription;
        default:
            throw new JungException(ErrorMessages.UNKNOWN_UNDO_ACTION);
        }
    }
}