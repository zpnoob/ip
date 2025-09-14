package jung.storage;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jung.task.Deadline;
import jung.task.Event;
import jung.task.Task;
import jung.task.ToDo;
import jung.util.DateFormats;
import jung.util.TaskType;
import java.nio.file.StandardOpenOption;
/**
 * Handles persistent storage of tasks to and from the file system.
 * Manages file creation, data serialization, and task reconstruction.
 */
public class Storage {

    private static final String FILE_DELIMITER = " \\| ";
    private static final int MINIMUM_PARTS = 3;
    private static final int TYPE_INDEX = 0;
    private static final int DONE_FLAG_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_TIME_INDEX = 3;
    private static final int EVENT_START_TIME_INDEX = 3;
    private static final int EVENT_END_TIME_INDEX = 4;
    private static final String DONE_FLAG = "1";

    private final Path filePath;

    private int skippedLinesCount = 0;

    /**
     * Creates a storage handler for the specified file path.
     * Automatically creates the file and parent directories if they don't exist.
     *
     * @param filePathString Path to the data storage file
     * @throws IOException If file or directory creation fails
     */
    public Storage(String filePathString) throws IOException {
        this.filePath = Paths.get(filePathString);
        ensureFileExists();
    }

    /**
     * Loads all tasks from the storage file.
     *
     * @return List of tasks loaded from persistent storage
     * @throws IOException If file reading fails
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        skippedLinesCount = 0;

        if (!Files.exists(filePath)) {
            return tasks; // Return empty list for new users
        }

        try {
            List<String> fileLines = Files.readAllLines(filePath);

            for (int lineNumber = 0; lineNumber < fileLines.size(); lineNumber++) {
                String line = fileLines.get(lineNumber);
                Task task = parseTaskFromLine(line, lineNumber + 1);
                if (task != null) {
                    tasks.add(task);
                }
            }

            // Inform user if some data was corrupted
            if (skippedLinesCount > 0) {
                System.err.println("Warning: Found " + skippedLinesCount +
                        " corrupted entries in data file. Your other tasks are safe!");
            }

        } catch (IOException e) {
            throw new IOException("Cannot read data file lah! Check if file is accessible: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves all tasks to the storage file.
     * Completely replaces the existing file content.
     *
     * @param tasks List of tasks to persist to storage
     * @throws IOException If file writing fails
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try {
            List<String> fileLines = new ArrayList<>();
            for (Task task : tasks) {
                String fileString = task.toFileString();
                if (fileString != null && !fileString.trim().isEmpty()) {
                    fileLines.add(fileString);
                }
            }

            Files.write(filePath, fileLines, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new IOException("Cannot save your tasks leh! Check if you have write permission: " +
                    e.getMessage());
        }
    }

    /**
     * Ensures the storage file and its parent directories exist.
     * Creates them if they don't already exist.
     *
     * @throws IOException If file or directory creation fails
     */
    private void ensureFileExists() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
    }

    /**
     * Parses a single line from the storage file into a Task object.
     *
     * @param line Line from the storage file
     * @return Parsed Task object, or null if parsing fails
     */
    private Task parseTaskFromLine(String line, int lineNumber) {
        if (line == null || line.trim().isEmpty()) {
            return null; // Skip empty lines silently
        }

        String[] parts = line.split(FILE_DELIMITER);

        if (parts.length < MINIMUM_PARTS) {
            System.err.println("Warning: Line " + lineNumber + " has invalid format, skipping...");
            skippedLinesCount++;
            return null;
        }

        try {
            // Enhanced validation
            if (parts[TYPE_INDEX].length() != 1) {
                throw new IllegalArgumentException("Invalid task type");
            }

            TaskType taskType = TaskType.fromSymbol(parts[TYPE_INDEX].charAt(0));
            boolean isCompleted = DONE_FLAG.equals(parts[DONE_FLAG_INDEX]);
            String description = parts[DESCRIPTION_INDEX].trim();

            // Validate description
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Empty task description");
            }

            Task task = createTaskByType(taskType, description, parts);

            if (isCompleted) {
                task.markAsDone();
            }

            return task;

        } catch (Exception e) {
            System.err.println("Warning: Line " + lineNumber + " is corrupted (" +
                    e.getMessage() + "), skipping...");
            skippedLinesCount++;
            return null;
        }
    }

    /**
     * Creates the appropriate task type based on the parsed data.
     *
     * @param taskType The type of task to create
     * @param description The task description
     * @param parts All parsed parts from the file line
     * @return Created task instance
     * @throws Exception If task creation fails
     */
    private Task createTaskByType(TaskType taskType, String description, String[] parts) throws Exception {
        switch (taskType) {
        case TODO:
            return new ToDo(description);
        case DEADLINE:
            LocalDateTime deadlineTime = LocalDateTime.parse(parts[DEADLINE_TIME_INDEX], DateFormats.INPUT_FORMAT);
            return new Deadline(description, deadlineTime);
        case EVENT:
            LocalDateTime startTime = LocalDateTime.parse(parts[EVENT_START_TIME_INDEX], DateFormats.INPUT_FORMAT);
            LocalDateTime endTime = LocalDateTime.parse(parts[EVENT_END_TIME_INDEX], DateFormats.INPUT_FORMAT);
            return new Event(description, startTime, endTime);
        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }
}

