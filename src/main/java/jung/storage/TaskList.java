package jung.storage;

import java.io.IOException;
import java.util.ArrayList;

import jung.JungException;
import jung.task.Task;

public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> loadedTasks, Storage storage) {
        this.tasks = loadedTasks;
        this.storage = storage;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task addTask(Task task) throws IOException {
        tasks.add(task);
        saveTasks();
        return task;
    }

    public Task deleteTask(int index) throws JungException, IOException {
        validateIndex(index);
        Task removed = tasks.remove(index);
        saveTasks();
        return removed;
    }

    public Task markTask(int index) throws JungException, IOException {
        validateIndex(index);
        Task t = tasks.get(index);
        t.markAsDone();
        saveTasks();
        return t;
    }

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
            throw new JungException("Invalid task number, please enter a valid number.");
        }
    }
}
