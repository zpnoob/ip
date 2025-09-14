package jung.storage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import jung.exceptions.JungException;
import jung.task.Task;
import jung.task.ToDo;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        // create empty TaskList for each test
        taskList = new TaskList(new ArrayList<>(), null);
    }

    @Test
    public void addTask_validTask_taskAdded() throws IOException {
        Task todo = new ToDo("test todo");
        taskList.addTask(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.getTasks().get(0));
    }

    @Test
    public void deleteTask_validIndex_taskDeleted() throws JungException, IOException {
        Task todo = new ToDo("delete me");
        taskList.addTask(todo);
        Task removed = taskList.deleteTask(0);
        assertEquals(todo, removed);
        assertEquals(0, taskList.size());
    }

    @Test
    public void deleteTask_invalidIndex_throwsJungException() {
        assertThrows(JungException.class, () -> taskList.deleteTask(0));
    }

    @Test
    public void markTask_validIndex_taskMarkedDone() throws JungException, IOException {
        Task todo = new ToDo("mark me");
        taskList.addTask(todo);
        Task marked = taskList.markTask(0);
        assertTrue(marked.isDone());
    }

    @Test
    public void unmarkTask_validIndex_taskMarkedNotDone() throws JungException, IOException {
        Task todo = new ToDo("unmark me");
        taskList.addTask(todo);
        taskList.markTask(0);
        Task unmarked = taskList.unmarkTask(0);
        assertFalse(unmarked.isDone());
    }

    @Test
    public void markTask_invalidIndex_throwsJungException() {
        assertThrows(JungException.class, () -> taskList.markTask(0));
    }

    @Test
    public void unmarkTask_invalidIndex_throwsJungException() {
        assertThrows(JungException.class, () -> taskList.unmarkTask(0));
    }
    @Test
    public void deleteTask_negativeIndex_throwsJungException() {
        assertThrows(JungException.class, () -> taskList.deleteTask(-1));
    }

    @Test
    public void deleteTask_indexTooLarge_throwsJungException() throws IOException {
        taskList.addTask(new ToDo("test"));
        assertThrows(JungException.class, () -> taskList.deleteTask(1));
    }

    @Test
    public void markTask_negativeIndex_throwsJungException() {
        assertThrows(JungException.class, () -> taskList.markTask(-1));
    }

    @Test
    public void unmarkTask_negativeIndex_throwsJungException() {
        assertThrows(JungException.class, () -> taskList.unmarkTask(-1));
    }

    @Test
    public void findTasksByKeyword_existingKeyword_returnsMatchingTasks() throws IOException {
        taskList.addTask(new ToDo("buy groceries"));
        taskList.addTask(new ToDo("buy books"));
        taskList.addTask(new ToDo("sell items"));

        ArrayList<Task> results = taskList.findTasksByKeyword("buy");

        assertEquals(2, results.size());
        assertEquals("buy groceries", results.get(0).getDescription());
        assertEquals("buy books", results.get(1).getDescription());
    }

    @Test
    public void findTasksByKeyword_nonExistentKeyword_returnsEmptyList() throws IOException {
        taskList.addTask(new ToDo("test task"));

        ArrayList<Task> results = taskList.findTasksByKeyword("nonexistent");

        assertTrue(results.isEmpty());
    }

    @Test
    public void findTasksByKeyword_caseInsensitive_returnsMatchingTasks() throws IOException {
        taskList.addTask(new ToDo("Buy Groceries"));

        ArrayList<Task> results = taskList.findTasksByKeyword("buy");

        assertEquals(1, results.size());
        assertEquals("Buy Groceries", results.get(0).getDescription());
    }

    @Test
    public void size_multipleOperations_returnsCorrectSize() throws IOException, JungException {
        assertEquals(0, taskList.size());

        taskList.addTask(new ToDo("task 1"));
        assertEquals(1, taskList.size());

        taskList.addTask(new ToDo("task 2"));
        assertEquals(2, taskList.size());

        taskList.deleteTask(0);
        assertEquals(1, taskList.size());
    }
}
