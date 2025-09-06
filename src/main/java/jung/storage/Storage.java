package jung.storage;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;

import jung.task.Deadline;
import jung.task.Event;
import jung.task.Task;
import jung.task.ToDo;

/**
 * Handles reading from and writing to the tasks data file.
 */
public class Storage {

    private final Path filePath;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";

    /**
     * Creates a Storage handler for the given file path.
     *
     * @param filePathStr Path to the data file.
     * @throws IOException If file or directory creation fails.
     */
    public Storage(String filePathStr) throws IOException {
        this.filePath = Paths.get(filePathStr);
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
    }

    /**
     * Loads tasks from the data file and returns a list of tasks.
     *
     * @return List of tasks loaded from file.
     * @throws IOException If reading file fails.
     */
    public ArrayList<Task> load() throws IOException {
        assert filePath != null : "File path must be set";

        ArrayList<Task> tasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            Task task = parseTaskLines(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    private Task parseTaskLines(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0];
        String doneFlag = parts[1];
        String desc = parts[2];
        Task task;
        try {
            switch (type) {
            case TASK_TYPE_TODO:
                task = new ToDo(desc);
                break;
            case TASK_TYPE_DEADLINE:
                LocalDateTime deadlineDate = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                task = new Deadline(desc, deadlineDate);
                break;
            case TASK_TYPE_EVENT:
                LocalDateTime fromDate = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                LocalDateTime toDate = LocalDateTime.parse(parts[4], DATE_TIME_FORMATTER);
                task = new Event(desc, fromDate, toDate);
                break;
            default:
                return null; // unknown task type
            }
            if ("1".equals(doneFlag)) {
                task.markAsDone();
            }
        } catch (Exception e) {
            return null;
        }
        return task;
    }

    /**
     * Saves the list of tasks to the data file.
     *
     * @param tasks List of tasks to save.
     * @throws IOException If writing to file fails.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toFileString());
        }
        Files.write(filePath, lines);
        // write content of the lines list to file at filePath, replacing what is in there
    }

}

