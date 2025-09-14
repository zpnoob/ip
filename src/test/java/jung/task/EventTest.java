package jung.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class EventTest {
    /*
    @Test
    public void constructor_invalidDateTimeArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> new Event("event task", null, null));
    }
     */

    @Test
    public void toFileString_validEvent_returnsCorrectFormat() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("event task", from, to);
        String expected = "E | 0 | event task | 28/8/2025 1000 | 28/8/2025 1200";
        assertEquals(expected, event.toFileString());
    }

    @Test
    public void toString_incompleteTask_returnsCorrectString() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("meeting", from, to);
        String expected = "[E][ ] meeting (from: 28 Aug 2025, 10:00 AM to: 28 Aug 2025, 12:00 PM)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void toString_completedTask_returnsCorrectString() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("meeting", from, to);
        event.markAsDone();
        String expected = "[E][X] meeting (from: 28 Aug 2025, 10:00 AM to: 28 Aug 2025, 12:00 PM)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void getStartTime_returnsCorrectStartTime() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("meeting", start, end);

        assertEquals(start, event.getStartTime());
    }

    @Test
    public void getEndTime_returnsCorrectEndTime() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("meeting", start, end);

        assertEquals(end, event.getEndTime());
    }

    @Test
    public void getTaskSymbol_returnsESymbol() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("meeting", start, end);

        assertEquals('E', event.getTaskSymbol());
    }

    @Test
    public void markAsDone_changesStatusCorrectly() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 28, 12, 0);
        Event event = new Event("meeting", start, end);

        assertFalse(event.isDone());
        event.markAsDone();
        assertTrue(event.isDone());
    }
}
