package jung.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ToDoTest {

    @Test
    public void constructor_validDescription_noExceptionThrown() {
        assertDoesNotThrow(() -> new ToDo("simple todo"));
    }

    @Test
    public void toFileString_incompleteTask_returnsCorrectString() {
        ToDo todo = new ToDo("buy groceries");
        String expected = "T | 0 | buy groceries";
        assertEquals(expected, todo.toFileString());
    }

    @Test
    public void toFileString_completedTask_returnsCorrectString() {
        ToDo todo = new ToDo("buy groceries");
        todo.markAsDone();
        String expected = "T | 1 | buy groceries";
        assertEquals(expected, todo.toFileString());
    }

    @Test
    public void toString_incompleteTask_returnsCorrectString() {
        ToDo todo = new ToDo("walk the dog");
        String expected = "[T][ ] walk the dog";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void toString_completedTask_returnsCorrectString() {
        ToDo todo = new ToDo("walk the dog");
        todo.markAsDone();
        String expected = "[T][X] walk the dog";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void markAsDone_initiallyIncomplete_becomesComplete() {
        ToDo todo = new ToDo("test task");
        assertFalse(todo.isDone());

        todo.markAsDone();
        assertTrue(todo.isDone());
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void markAsNotDone_initiallyComplete_becomesIncomplete() {
        ToDo todo = new ToDo("test task");
        todo.markAsDone();

        todo.markAsNotDone();
        assertFalse(todo.isDone());
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void getDescription_returnsCorrectDescription() {
        ToDo todo = new ToDo("my task");
        assertEquals("my task", todo.getDescription());
    }

    @Test
    public void getTaskSymbol_returnsTSymbol() {
        ToDo todo = new ToDo("test");
        assertEquals('T', todo.getTaskSymbol());
    }
}

