package jung.storage;

import java.io.IOException;
import jung.exceptions.JungException;
import jung.task.Task;

/**
 * Represents an action that can be undone, storing the necessary information
 * to reverse the operation.
 */
public class UndoableAction {
    public enum ActionType {
        ADD_TASK,
        DELETE_TASK,
        MARK_TASK,
        UNMARK_TASK
    }

    private final ActionType actionType;
    private final Task task;
    private final int index;
    private final String description;

    /**
     * Creates an UndoableAction for add operations.
     */
    public UndoableAction(ActionType actionType, Task task, String description) {
        this.actionType = actionType;
        this.task = task;
        this.index = -1; // Not needed for add operations
        this.description = description;
    }

    /**
     * Creates an UndoableAction for operations that require an index.
     */
    public UndoableAction(ActionType actionType, Task task, int index, String description) {
        this.actionType = actionType;
        this.task = task;
        this.index = index;
        this.description = description;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Task getTask() {
        return task;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Executes the undo operation on the given TaskList.
     */
    public String executeUndo(TaskList taskList) throws JungException, IOException {
        switch (actionType) {
        case ADD_TASK:
            // Undo add by removing the last added task
            taskList.removeLastTask();
            return "Undone: " + description;
        case DELETE_TASK:
            // Undo delete by re-adding the task at the original index
            taskList.insertTaskAt(task, index);
            return "Undone: " + description;
        case MARK_TASK:
            // Undo mark by unmarking the task
            taskList.unmarkTaskSilently(index);
            return "Undone: " + description;
        case UNMARK_TASK:
            // Undo unmark by marking the task
            taskList.markTaskSilently(index);
            return "Undone: " + description;
        default:
            throw new JungException("Unknown action type for undo.");
        }
    }


}
