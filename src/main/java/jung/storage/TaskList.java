package jung.storage;

import java.io.IOException;
import java.util.ArrayList;

import jung.exceptions.JungException;
import jung.task.Task;

/**
 * Represents the list of tasks and provides functionality to manage them.
 */
public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList loaded with existing tasks and attached storage.
     *
     * @param loadedTasks List of previously loaded tasks.
     * @param storage Storage handler for saving tasks.
     */
    public TaskList(ArrayList<Task> loadedTasks, Storage storage) {
        this.tasks = loadedTasks;
        this.storage = storage;
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
        saveTasks();
        return t;
    }

    public ArrayList<Task> findTasksByKeyword(String keyword) {
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(task);
            }
        }
        return results;
    }

    private void saveTasks() throws IOException {
        if (storage != null) {
            storage.save(tasks);
        }
    }

    private void validateIndex(int index) throws JungException {
        if (index < 0 || index >= tasks.size()) {
            throw new JungException("Invalid task number, please enter " +
                    "a valid number.");
        }
    }
}
