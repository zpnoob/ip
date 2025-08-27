import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddDeadlineCommand extends Command {

    private String description;
    private String byStr;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public AddDeadlineCommand(String description, String byStr) {
        this.description = description;
        this.byStr = byStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, JungException {
        try {
            LocalDateTime by = LocalDateTime.parse(byStr, formatter);
            Task newTask = new Deadline(description, by);
            tasks.addTask(newTask);
            ui.showAddTask(newTask, tasks.size());
        } catch (DateTimeParseException e) {
            throw new JungException("Invalid date/time format. Please use d/M/yyyy HHmm.");
        }
    }
}
