package jung.command;

import jung.exceptions.JungException;
import jung.task.Task;
import jung.task.ToDo;

/**
 * Command to add a simple todo task without deadlines or time constraints.
 * This allows users to track basic tasks that need to be completed.
 */
public class AddTodoCommand extends AddTaskCommand {

    private final String taskDescription;

    /**
     * Creates a command to add a todo task with the given description.
     *
     * @param taskDescription What the user needs to do
     */
    public AddTodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Creates a new Todo task with the provided description.
     *
     * @return A new Todo task instance
     * @throws JungException This implementation doesn't throw exceptions
     */
    @Override
    protected Task createTask() throws JungException {
        return new ToDo(taskDescription);
    }
}