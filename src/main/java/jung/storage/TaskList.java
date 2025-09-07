package jung.storage;

import java.io.IOException;
import java.util.ArrayList;

import jung.exceptions.JungException;
import jung.task.Task;

/**
 * Represents the list of tasks and provides functionality to manage them.
 * Now includes undo functionality for the most recent command
 */
public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;
    private UndoableAction lastAction;

    /**
     * Creates a TaskList loaded with existing tasks and attached storage.
     *
     * @param loadedTasks List of previously loaded tasks.
     * @param storage Storage handler for saving tasks.
     */
    public TaskList(ArrayList<Task> loadedTasks, Storage storage) {
        this.tasks = loadedTasks;
        this.storage = storage;
        this.lastAction = null;
    }

    /**
     * Returns the list of tasks.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Adds a new task and saves the tasks.
     *
     * @param task Task to add.
     * @return The task added.
     * @throws IOException If saving fails.
     */
    public Task addTask(Task task) throws IOException {
        tasks.add(task);
        // Store undo information
        lastAction = new UndoableAction(
                UndoableAction.ActionType.ADD_TASK,
                task,
                "added task: " + task
        );
        saveTasks();
        return task;
    }

    /**
     * Deletes a task at the given index after validation.
     *
     * @param index Index of task to delete.
     * @return The removed task.
     * @throws JungException If index invalid.
     * @throws IOException If saving fails.
     */
    public Task deleteTask(int index) throws JungException, IOException {
        validateIndex(index);
        Task removed = tasks.remove(index);
        // Store undo information
        lastAction = new UndoableAction(
                UndoableAction.ActionType.DELETE_TASK,
                removed,
                index,
                "removed task: " + removed
        );
        saveTasks();
        return removed;
    }

    /**
     * Marks a task as done.
     *
     * @param index Index of task to mark.
     * @return The marked task.
     * @throws JungException If index invalid.
     * @throws IOException If saving fails.
     */
    public Task markTask(int index) throws JungException, IOException {
        validateIndex(index);
        Task t = tasks.get(index);
        t.markAsDone();
        lastAction = new UndoableAction(
                UndoableAction.ActionType.MARK_TASK,
                t,
                index,
                "marked as done: " + t
        );
        saveTasks();
        return t;
    }

    /**
     * Marks a task as not done.
     *
     * @param index Index of task to unmark.
     * @return The unmarked task.
     * @throws JungException If index invalid.
     * @throws IOException If saving fails.
     */
    public Task unmarkTask(int index) throws JungException, IOException {
        validateIndex(index);
        Task t = tasks.get(index);
        t.markAsNotDone();
        lastAction = new UndoableAction(
                UndoableAction.ActionType.UNMARK_TASK,
                t,
                index,
                "marked as not done: " + t
        );
        saveTasks();
        return t;
    }

    /**
     * Gets the last undoable action.
     *
     * @return The last UndoableAction or null if none exists.
     */
    public UndoableAction getLastAction() {
        return lastAction;
    }

    /**
     * Clears the last action (used after undo is performed).
     */
    public void clearLastAction() {
        lastAction = null;
    }

    /**
     * Undoes the last undoable command.
     *
     * @return Result message of the undo operation.
     * @throws JungException If no command to undo.
     * @throws IOException If saving fails.
     */

    public ArrayList<Task> findTasksByKeyword(String keyword) {
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(task);
            }
        }
        return results;
    }

    // Helper methods for undo operations
    // Needed because the normal deleteTask() method creates its own undo action

    /**
     * Removes the last task without creating an undo action (used for undo operations).
     */
    void removeLastTask() throws JungException {
        if (tasks.isEmpty()) {
            throw new JungException("No tasks to remove.");
        }
        tasks.remove(tasks.size() - 1);
    }

    /**
     * Inserts a task at a specific index without creating an undo action.
     */
    void insertTaskAt(Task task, int index) {
        if (index >= tasks.size()) {
            tasks.add(task);
        } else {
            tasks.add(index, task);
        }
    }

    /**
     * Marks a task without creating an undo action (used for undo operations).
     */
    void markTaskSilently(int index) throws JungException {
        validateIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task without creating an undo action (used for undo operations).
     */
    void unmarkTaskSilently(int index) throws JungException {
        validateIndex(index);
        tasks.get(index).markAsNotDone();
    }

    private void saveTasks() throws IOException {
        if (storage != null) {
            storage.save(tasks);
        }
    }

    private void validateIndex(int index) throws JungException {
        assert index >= 0 : "Index should not be negative";
        assert tasks != null : "Tasks list should be initialized";
        assert index < tasks.size() : "Index should be less than the number of tasks";
        if (index < 0 || index >= tasks.size()) {
            throw new JungException("Invalid task number, " +
                    "please enter a valid number.");
        }
    }
}
