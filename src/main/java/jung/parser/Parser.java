package jung.parser;

import jung.exceptions.JungException;
import jung.command.*;
import jung.util.ErrorMessages;

/**
 * Parser responsible for converting user input strings into executable Command objects.
 * Handles command validation, parameter extraction, and command instantiation.
 */
public class Parser {

    // Command word constants
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";
    private static final String CMD_UNDO = "undo";

    // Command length constants
    private static final int TODO_COMMAND_LENGTH = 4;
    private static final int DEADLINE_COMMAND_LENGTH = 8;
    private static final int EVENT_COMMAND_LENGTH = 5;
    private static final int FIND_COMMAND_LENGTH = 4;

    // Keyword constants
    private static final String DEADLINE_KEYWORD = "/by";
    private static final String EVENT_FROM_KEYWORD = "/from";
    private static final String EVENT_TO_KEYWORD = "/to";

    /**
     * Parses user input into an appropriate Command instance.
     * Follows the happy path: validate input, extract command word, create command.
     *
     * @param input Raw user input string
     * @return Appropriate Command object ready for execution
     * @throws JungException If input format is invalid or command is unknown
     */
    public static Command parse(String input) throws JungException {
        validateInput(input);

        String trimmedInput = input.trim();
        String commandWord = extractCommandWord(trimmedInput);

        return createCommand(commandWord, trimmedInput);
    }

    /**
     * Validates that user input is not null or empty.
     *
     * @param input User input to validate
     * @throws JungException If input is invalid
     */
    private static void validateInput(String input) throws JungException {
        if (input == null || input.trim().isEmpty()) {
            throw new JungException(ErrorMessages.EMPTY_INPUT);
        }

        if (input.length() > 500) {
            throw new JungException("Wah, your command too long liao! Keep it shorter can?");
        }

        if (input.contains(" | ") && !input.trim().toLowerCase().startsWith("find")) {
            throw new JungException("Cannot use ' | ' in your command lah! It's reserved!");
        }
    }

    /**
     * Extracts the command word (first word) from user input.
     *
     * @param input Trimmed user input
     * @return The command word in lowercase
     */
    private static String extractCommandWord(String input) {
        String normalized = input.trim().replaceAll("\\s+", " ");
        int firstSpaceIndex = normalized.indexOf(" ");
        if (firstSpaceIndex == -1) {
            return normalized.toLowerCase();
        }
        return normalized.substring(0, firstSpaceIndex).toLowerCase();
    }
    /**
     * Creates the appropriate Command object based on the command word.
     *
     * @param commandWord The parsed command word
     * @param fullInput The complete user input
     * @return Command object ready for execution
     * @throws JungException If command is unknown or has invalid format
     */
    private static Command createCommand(String commandWord, String fullInput) throws JungException {
        switch (commandWord) {
        case CMD_BYE:
            return new ExitCommand();
        case CMD_LIST:
            return new ListCommand();
        case CMD_TODO:
            return createTodoCommand(fullInput);
        case CMD_DEADLINE:
            return createDeadlineCommand(fullInput);
        case CMD_EVENT:
            return createEventCommand(fullInput);
        case CMD_MARK:
            return createModifyCommand(ModifyTaskCommand.Action.MARK, fullInput);
        case CMD_UNMARK:
            return createModifyCommand(ModifyTaskCommand.Action.UNMARK, fullInput);
        case CMD_DELETE:
            return createModifyCommand(ModifyTaskCommand.Action.DELETE, fullInput);
        case CMD_FIND:
            return createFindCommand(fullInput);
        case CMD_UNDO:
            return new UndoCommand();
        default:
            throw new JungException(ErrorMessages.UNKNOWN_COMMAND);
        }
    }

    /**
     * Creates a todo command by extracting the task description.
     *
     * @param input Full todo command input
     * @return AddTodoCommand with the task description
     * @throws JungException If description is empty
     */
    private static Command createTodoCommand(String input) throws JungException {
        String normalized = input.trim().replaceAll("\\s+", " ");
        String taskDescription = normalized.substring(TODO_COMMAND_LENGTH).trim();

        if (taskDescription.isEmpty()) {
            throw new JungException(ErrorMessages.EMPTY_TODO_DESCRIPTION);
        }
        if (taskDescription.length() > 200) {
            throw new JungException("Your task description too long lah! Keep it under 200 characters!");
        }
        return new AddTodoCommand(taskDescription);
    }

    /**
     * Creates a deadline command by parsing description and deadline time.
     *
     * @param input Full deadline command input
     * @return AddDeadlineCommand with description and deadline
     * @throws JungException If format is invalid or fields are missing
     */
    private static Command createDeadlineCommand(String input) throws JungException {
        String normalized = input.trim().replaceAll("\\s+", " ");
        int deadlineKeywordIndex = normalized.indexOf(DEADLINE_KEYWORD);

        if (deadlineKeywordIndex == -1) {
            throw new JungException(ErrorMessages.MISSING_DEADLINE_DATE);
        }

        String taskDescription = normalized.substring(DEADLINE_COMMAND_LENGTH, deadlineKeywordIndex).trim();
        String deadlineTime = normalized.substring(deadlineKeywordIndex + DEADLINE_KEYWORD.length()).trim();

        if (taskDescription.isEmpty() || deadlineTime.isEmpty()) {
            throw new JungException(ErrorMessages.EMPTY_DEADLINE_FIELDS);
        }

        if (taskDescription.length() > 200) {
            throw new JungException("Your task description too long lah! Keep it shorter!");
        }

        return new AddDeadlineCommand(taskDescription, deadlineTime);
    }

    /**
     * Creates an event command by parsing description, start time, and end time.
     *
     * @param input Full event command input
     * @return AddEventCommand with description and time range
     * @throws JungException If format is invalid or fields are missing
     */
    private static Command createEventCommand(String input) throws JungException {
        String normalized = input.trim().replaceAll("\\s+", " ");
        int fromKeywordIndex = normalized.indexOf(EVENT_FROM_KEYWORD);
        int toKeywordIndex = normalized.indexOf(EVENT_TO_KEYWORD);

        boolean missingFromKeyword = fromKeywordIndex == -1;
        boolean missingToKeyword = toKeywordIndex == -1;
        boolean invalidKeywordOrder = fromKeywordIndex > toKeywordIndex;

        if (missingFromKeyword || missingToKeyword || invalidKeywordOrder) {
            throw new JungException(ErrorMessages.MISSING_EVENT_DATES);
        }

        String taskDescription = normalized.substring(EVENT_COMMAND_LENGTH, fromKeywordIndex).trim();
        String startTime = normalized.substring(fromKeywordIndex + EVENT_FROM_KEYWORD.length(), toKeywordIndex).trim();
        String endTime = normalized.substring(toKeywordIndex + EVENT_TO_KEYWORD.length()).trim();


        if (taskDescription.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new JungException(ErrorMessages.EMPTY_EVENT_FIELDS);
        }

        if (taskDescription.length() > 200) {
            throw new JungException("Your task description too long lah! Keep it shorter!");
        }

        return new AddEventCommand(taskDescription, startTime, endTime);
    }

    /**
     * Creates a task modification command (mark/unmark/delete) with task number.
     *
     * @param action The modification action to perform
     * @param input Full command input
     * @return ModifyTaskCommand with action and task index
     * @throws JungException If task number is missing or invalid
     */
    private static Command createModifyCommand(ModifyTaskCommand.Action action, String input)
            throws JungException {

        String actionName = action.name().toLowerCase();
        String taskNumberString = input.substring(actionName.length()).trim();

        if (taskNumberString.isEmpty()) {
            throw new JungException(actionName + ErrorMessages.MISSING_TASK_NUMBER);
        }

        try {
            int taskNumber = Integer.parseInt(taskNumberString);
            int zeroBasedIndex = taskNumber - 1;
            return new ModifyTaskCommand(action, zeroBasedIndex);
        } catch (NumberFormatException e) {
            throw new JungException(ErrorMessages.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Creates a find command by extracting the search keyword.
     *
     * @param input Full find command input
     * @return FindCommand with the search keyword
     * @throws JungException If keyword is missing
     */
    private static Command createFindCommand(String input) throws JungException {
        String searchKeyword = input.substring(FIND_COMMAND_LENGTH).trim();

        if (searchKeyword.isEmpty()) {
            throw new JungException(ErrorMessages.MISSING_FIND_KEYWORD);
        }

        return new FindCommand(searchKeyword);
    }
}