package jung.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class DeadlineTest {

    @Test
    public void constructor_validInput_createsDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        assertDoesNotThrow(() -> new Deadline("homework", deadline));
    }

    @Test
    public void getDeadlineTime_returnsCorrectTime() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);

        assertEquals(deadline, task.getDeadlineTime());
    }

    @Test
    public void getTaskSymbol_returnsDSymbol() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);

        assertEquals('D', task.getTaskSymbol());
    }

    @Test
    public void toFileString_incompleteTask_returnsCorrectFormat() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);

        String expected = "D | 0 | homework | 15/3/2025 1430";
        assertEquals(expected, task.toFileString());
    }

    @Test
    public void toFileString_completedTask_returnsCorrectFormat() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);
        task.markAsDone();

        String expected = "D | 1 | homework | 15/3/2025 1430";
        assertEquals(expected, task.toFileString());
    }

    @Test
    public void toString_incompleteTask_returnsCorrectString() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);

        String expected = "[D][ ] homework (by: 15 Mar 2025, 2:30 PM)";
        assertEquals(expected, task.toString());
    }

    @Test
    public void toString_completedTask_returnsCorrectString() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);
        task.markAsDone();

        String expected = "[D][X] homework (by: 15 Mar 2025, 2:30 PM)";
        assertEquals(expected, task.toString());
    }

    @Test
    public void markAsDone_changesStatusCorrectly() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 15, 14, 30);
        Deadline task = new Deadline("homework", deadline);

        assertFalse(task.isDone());
        task.markAsDone();
        assertTrue(task.isDone());
    }
}
