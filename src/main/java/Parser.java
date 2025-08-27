public class Parser {
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
            String todoDesc = trimmedInput.substring(4).trim();
            if (todoDesc.isEmpty()) {
                throw new JungException("The description of a todo cannot be empty. Try: todo [description]");
            }
            return new AddTodoCommand(todoDesc);
        case "deadline":
            // parse deadline format: deadline desc /by datetime
            int byIndex = trimmedInput.indexOf("/by");
            if (byIndex == -1) {
                throw new JungException("Deadline task requires a '/by' date/time. Format: deadline [desc] /by [datetime]");
            }
            String deadlineDesc = trimmedInput.substring(8, byIndex).trim();
            String by = trimmedInput.substring(byIndex + 3).trim();
            if (deadlineDesc.isEmpty() || by.isEmpty()) {
                throw new JungException("Deadline description or date/time cannot be empty.");
            }
            return new AddDeadlineCommand(deadlineDesc, by);
        case "event":
            // parse event format: event desc /from datetime /to datetime
            int fromIndex = trimmedInput.indexOf("/from");
            int toIndex = trimmedInput.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                throw new JungException("Event task requires both '/from' and '/to' date/time.");
            }
            String eventDesc = trimmedInput.substring(5, fromIndex).trim();
            String from = trimmedInput.substring(fromIndex + 5, toIndex).trim();
            String to = trimmedInput.substring(toIndex + 3).trim();
            if (eventDesc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new JungException("Event description, start, and end date/time cannot be empty.");
            }
            return new AddEventCommand(eventDesc, from, to);
        case "mark":
        case "unmark":
        case "delete":
            String arg = trimmedInput.substring(commandWord.length()).trim();
            if (arg.isEmpty()) {
                throw new JungException(commandWord + " command requires a task number.");
            }
            int index;
            try {
                index = Integer.parseInt(arg) - 1;  // zero-based internally
            } catch (NumberFormatException e) {
                throw new JungException("Invalid task number format.");
            }
            return new ModifyTaskCommand(commandWord, index);
        default:
            throw new JungException("Sorry, I don't recognise that command.");
        }
    }

    private static String getFirstWord(String text) {
        int spaceIndex = text.indexOf(" ");
        if (spaceIndex == -1) {
            return text;
        }
        return text.substring(0, spaceIndex);
    }
}


