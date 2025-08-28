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
}

