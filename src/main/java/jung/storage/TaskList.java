package jung.storage;

import java.io.IOException;
import java.util.ArrayList;
import jung.exceptions.JungException;
import jung.task.Task;
import jung.util.ErrorMessages;

/**
 * Manages the collection of tasks and provides operations for task manipulation.
 * Includes undo functionality to reverse the most recent operation.
 */
public class TaskList {

    private final ArrayList<Task> tasks;
    private final Storage storage;
    private UndoableAction lastAction;

    /**
     * Creates a TaskList with existing tasks and storage backend.
     *
     * @param loadedTasks Previously saved tasks to initialize with
     * @param storage Storage system for automatic persistence
     */
    public TaskList(ArrayList<Task> loadedTasks, Storage storage) {
        this.tasks = loadedTasks;
        this.storage = storage;
        this.lastAction = null;
    }

    /**
     * Gets all tasks in the list.
     *
     * @return Complete list of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return Total task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a new task to the list and saves to storage.
     * Creates an undo point for this operation.
     *
     * @param task Task to add to the list
     * @return The added task
     * @throws IOException If storage save fails
     */
    public Task addTask(Task task) throws IOException {
        tasks.add(task);
        recordUndoableAction(UndoableAction.ActionType.ADD_TASK, task, "added task: " + task);
        saveToStorage();
        return task;
    }

    /**
     * Removes a task at the specified index.
     * Creates an undo point for this operation.
     *
     * @param index Zero-based index of task to remove
     * @return The removed task
     * @throws JungException If index is invalid
     * @throws IOException If storage save fails
     */
    public Task deleteTask(int index) throws JungException, IOException {
        validateTaskIndex(index);
        Task removedTask = tasks.remove(index);
        recordUndoableAction(UndoableAction.ActionType.DELETE_TASK, removedTask, index, "removed task: " + removedTask);
        saveToStorage();
        return removedTask;
    }

    /**
     * Marks a task as completed.
     * Creates an undo point for this operation.
     *
     * @param index Zero-based index of task to mark
     * @return The marked task
     * @throws JungException If index is invalid
     * @throws IOException If storage save fails
     */
    public Task markTask(int index) throws JungException, IOException {
        validateTaskIndex(index);
        Task task = tasks.get(index);
        task.markAsDone();
        recordUndoableAction(UndoableAction.ActionType.MARK_TASK, task, index, "marked as done: " + task);
        saveToStorage();
        return task;
    }

    /**
     * Marks a task as not completed.
     * Creates an undo point for this operation.
     *
     * @param index Zero-based index of task to unmark
     * @return The unmarked task
     * @throws JungException If index is invalid
     * @throws IOException If storage save fails
     */
    public Task unmarkTask(int index) throws JungException, IOException {
        validateTaskIndex(index);
        Task task = tasks.get(index);
        task.markAsNotDone();
        recordUndoableAction(UndoableAction.ActionType.UNMARK_TASK, task, index, "marked as not done: " + task);
        saveToStorage();
        return task;
    }

    /**
     * Searches for tasks containing the specified keyword in their descriptions.
     *
     * @param keyword Text to search for (case-insensitive)
     * @return List of matching tasks
     */
    public ArrayList<Task> findTasksByKeyword(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowercaseKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (taskDescriptionContains(task, lowercaseKeyword)) {
                matchingTasks.add(task);
            }
        }

        return matchingTasks;
    }

    /**
     * Gets the last undoable action that was performed.
     *
     * @return Last undoable action, or null if none exists
     */
    public UndoableAction getLastAction() {
        return lastAction;
    }

    /**
     * Clears the stored undo action (used after undo is performed).
     */
    public void clearLastAction() {
        lastAction = null;
    }

    // ============= UNDO SUPPORT METHODS =============

    /**
     * Removes the last task without creating an undo action.
     * Used internally by undo operations.
     */
    void removeLastTask() throws JungException {
        if (tasks.isEmpty()) {
            throw new JungException(ErrorMessages.NO_TASKS_TO_REMOVE);
        }
        tasks.remove(tasks.size() - 1);
    }

    /**
     * Inserts a task at the specified index without creating an undo action.
     * Used internally by undo operations.
     */
    void insertTaskAt(Task task, int index) {
        if (index >= tasks.size()) {
            tasks.add(task);
        } else {
            tasks.add(index, task);
        }
    }

    /**
     * Marks a task as done without creating an undo action.
     * Used internally by undo operations.
     */
    void markTaskSilently(int index) throws JungException {
        validateTaskIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as not done without creating an undo action.
     * Used internally by undo operations.
     */
    void unmarkTaskSilently(int index) throws JungException {
        validateTaskIndex(index);
        tasks.get(index).markAsNotDone();
    }

    // ============= PRIVATE HELPER METHODS =============

    /**
     * Records an undoable action for add operations.
     */
    private void recordUndoableAction(UndoableAction.ActionType actionType, Task task, String description) {
        lastAction = new UndoableAction(actionType, task, description);
    }

    /**
     * Records an undoable action for operations requiring an index.
     */
    private void recordUndoableAction(UndoableAction.ActionType actionType, Task task, int index, String description) {
        lastAction = new UndoableAction(actionType, task, index, description);
    }

    /**
     * Saves the current task list to persistent storage.
     */
    private void saveToStorage() throws IOException {
        if (storage != null) {
            storage.save(tasks);
        }
    }

    /**
     * Validates that a task index is within valid bounds.
     */
    private void validateTaskIndex(int index) throws JungException {
        if (index < 0 || index >= tasks.size()) {
            throw new JungException(ErrorMessages.INVALID_TASK_INDEX);
        }
    }

    /**
     * Checks if a task's description contains the given keyword.
     */
    private boolean taskDescriptionContains(Task task, String keyword) {
        return task.getDescription().toLowerCase().contains(keyword);
    }
}