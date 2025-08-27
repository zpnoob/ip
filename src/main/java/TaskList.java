import java.io.IOException;
import java.util.ArrayList;

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

    public void addTask(Task task) throws IOException {
        tasks.add(task);
        saveTasks();
        System.out.println("Okay. I've added this task:");
        System.out.println(task);
        System.out.println("You now have " + tasks.size() + " tasks in the list.");
        System.out.println();
    }

    public void deleteTask(int index) throws JungException, IOException {
        validateIndex(index);
        Task removed = tasks.remove(index);
        saveTasks();
        System.out.println("Okay. I've removed this task:");
        System.out.println(removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println();
    }

    public void markTask(int index) throws JungException, IOException {
        validateIndex(index);
        tasks.get(index).markAsDone();
        saveTasks();
        System.out.println("Lame, this task is marked as done.");
        System.out.println(tasks.get(index));
        System.out.println();
    }

    public void unmarkTask(int index) throws JungException, IOException {
        validateIndex(index);
        tasks.get(index).markAsNotDone();
        saveTasks();
        System.out.println("I've marked this task as not done yet.");
        System.out.println(tasks.get(index));
        System.out.println();
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is currently empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println();
    }

    private void saveTasks() throws IOException {
        if (storage != null) {
            storage.save(tasks);
        }
    }

    private void validateIndex(int index) throws JungException {
        if (index < 0 || index >= tasks.size()) {
            throw new JungException("invalid task number, please enter a valid number.");
        }
    }
}
