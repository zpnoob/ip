package jung.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jung.command.*;
import jung.exceptions.JungException;

/**
 * Tests for the Parser class covering common scenarios and edge cases.
 */
public class ParserTest {

    @Test
    public void parse_validTodoCommand_returnsAddTodoCommand() throws JungException {
        Command result = Parser.parse("todo buy groceries");
        assertTrue(result instanceof AddTodoCommand);
    }

    @Test
    public void parse_todoWithExtraSpaces_returnsAddTodoCommand() throws JungException {
        Command result = Parser.parse("  todo    buy   groceries  ");
        assertTrue(result instanceof AddTodoCommand);
    }

    @Test
    public void parse_emptyTodoDescription_throwsJungException() {
        JungException exception = assertThrows(JungException.class,
                () -> Parser.parse("todo   "));
        assertTrue(exception.getMessage().contains("description missing"));
    }

    @Test
    public void parse_tooLongTodoDescription_throwsJungException() {
        String longDescription = "a".repeat(250);
        assertThrows(JungException.class,
                () -> Parser.parse("todo " + longDescription));
    }

    @Test
    public void parse_validDeadlineCommand_returnsAddDeadlineCommand() throws JungException {
        Command result = Parser.parse("deadline homework /by 15/3/2024 1400");
        assertTrue(result instanceof AddDeadlineCommand);
    }

    @Test
    public void parse_deadlineWithoutByKeyword_throwsJungException() {
        assertThrows(JungException.class,
                () -> Parser.parse("deadline homework 15/3/2024 1400"));
    }

    @Test
    public void parse_validEventCommand_returnsAddEventCommand() throws JungException {
        Command result = Parser.parse("event meeting /from 15/3/2024 1400 /to 15/3/2024 1600");
        assertTrue(result instanceof AddEventCommand);
    }

    @Test
    public void parse_eventWithMissingKeywords_throwsJungException() {
        assertThrows(JungException.class,
                () -> Parser.parse("event meeting 15/3/2024 1400"));
    }

    @Test
    public void parse_validMarkCommand_returnsModifyTaskCommand() throws JungException {
        Command result = Parser.parse("mark 1");
        assertTrue(result instanceof ModifyTaskCommand);
    }

    @Test
    public void parse_markWithInvalidNumber_throwsJungException() {
        assertThrows(JungException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void parse_emptyInput_throwsJungException() {
        assertThrows(JungException.class, () -> Parser.parse(""));
        assertThrows(JungException.class, () -> Parser.parse("   "));
    }

    @Test
    public void parse_inputTooLong_throwsJungException() {
        String longInput = "todo " + "a".repeat(500);
        assertThrows(JungException.class, () -> Parser.parse(longInput));
    }

    @Test
    public void parse_inputWithReservedChars_throwsJungException() {
        assertThrows(JungException.class,
                () -> Parser.parse("todo task with | reserved chars"));
    }

    @Test
    public void parse_unknownCommand_throwsJungException() {
        assertThrows(JungException.class,
                () -> Parser.parse("unknown command"));
    }

    @Test
    public void parse_listCommand_returnsListCommand() throws JungException {
        Command result = Parser.parse("list");
        assertTrue(result instanceof ListCommand);
    }

    @Test
    public void parse_byeCommand_returnsExitCommand() throws JungException {
        Command result = Parser.parse("bye");
        assertTrue(result instanceof ExitCommand);
    }

    @Test
    public void parse_findCommand_returnsFindCommand() throws JungException {
        Command result = Parser.parse("find book");
        assertTrue(result instanceof FindCommand);
    }
}
