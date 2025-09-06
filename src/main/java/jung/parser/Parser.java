package jung.parser;

import jung.exceptions.JungException;
import jung.command.AddDeadlineCommand;
import jung.command.AddEventCommand;
import jung.command.AddTodoCommand;
import jung.command.Command;
import jung.command.ExitCommand;
import jung.command.FindCommand;
import jung.command.ListCommand;
import jung.command.ModifyTaskCommand;

/**
 * Parser to parse user input strings into Command objects.
 */
public class Parser {

    /**
     * Parses the raw user input into an appropriate Command instance.
     *
     * @param input Raw input string.
     * @return Parsed Command object.
     * @throws JungException If input format is invalid or command unknown.
     */

    public static Command parse(String input) throws JungException {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new JungException("Input cannot be empty.");
        }
        String commandWord = getFirstWord(trimmedInput).toLowerCase();
        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "todo":
            return parseTodoCommand(trimmedInput);
        case "deadline":
            return parseDeadlineCommand(trimmedInput);
        case "event":
            return parseEventCommand(trimmedInput);
        case "mark":
        case "unmark":
        case "delete":
            return parseModifyCommand(commandWord, trimmedInput);
        case "find":
            return parseFindCommand(trimmedInput);
        default:
            throw new JungException("Sorry, I don't recognise that command.");
        }
    }

    /**
     * Helper method to get the first word in a string.
     */
    private static String getFirstWord(String text) {
        int spaceIndex = text.indexOf(" ");
        if (spaceIndex == -1) {
            return text;
        }
        return text.substring(0, spaceIndex);
    }

    /**
     * Parses a 'todo' command input string, extracting the description.
     *
     * @param input The full todo command input string.
     * @return An AddTodoCommand object initialized with the extracted description.
     * @throws JungException If the todo description is empty.
     */
    private static Command parseTodoCommand(String input) throws JungException {
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            throw new JungException("The description of a todo cannot be empty. Try: todo [description]");
        }
        return new AddTodoCommand(desc);
    }

    /**
     * Parses a 'deadline' command input string, extracting the description and deadline time.
     *
     * @param input The full deadline command input string.
     * @return An AddDeadlineCommand object initialized with the description and datetime.
     * @throws JungException If the input format is invalid or required fields are missing.
     */
    private static Command parseDeadlineCommand(String input) throws JungException {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            throw new JungException("Deadline task requires a '/by' date/time. Format: deadline [desc] /by [datetime]");
        }
        String desc = input.substring(8, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new JungException("Deadline description or date/time cannot be empty.");
        }
        return new AddDeadlineCommand(desc, by);
    }

    /**
     * Parses an 'event' command input string, extracting the description and time range.
     *
     * @param input The full event command input string.
     * @return An AddEventCommand object initialized with description, start, and end times.
     * @throws JungException If the input format is invalid or required fields are missing.
     */
    private static Command parseEventCommand(String input) throws JungException {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            throw new JungException("Event task requires both '/from' and '/to' date/time.");
        }
        String desc = input.substring(5, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new JungException("Event description, start, and end date/time cannot be empty.");
        }
        return new AddEventCommand(desc, from, to);
    }

    /**
     * Parses commands that modify tasks such as 'mark', 'unmark', or 'delete'.
     *
     * @param action The action string representing the command type.
     * @param input The full command input string.
     * @return A ModifyTaskCommand object initialized with action and task index.
     * @throws JungException If the task number is missing or invalid.
     */
    private static Command parseModifyCommand(String action, String input) throws JungException {
        String arg = input.substring(action.length()).trim();
        if (arg.isEmpty()) {
            throw new JungException(action + " command requires a task number.");
        }
        int index;
        try {
            index = Integer.parseInt(arg) - 1;  // zero-based index
        } catch (NumberFormatException e) {
            throw new JungException("Invalid task number format.");
        }
        return new ModifyTaskCommand(action, index);
    }

    /**
     * Parses a 'find' command input string, extracting the keyword to search.
     *
     * @param input The full find command input string.
     * @return A FindCommand object initialized with the extracted search keyword.
     * @throws JungException If the find keyword is missing.
     */
    private static Command parseFindCommand(String input) throws JungException {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new JungException("The find command requires a keyword to search.");
        }
        return new FindCommand(keyword);
    }
}


