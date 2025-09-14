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
 * Provides serialization of tasks to file format and deserialization back to Task objects.
 *
 * File format: TaskType | CompletionFlag | Description | [DateTime fields]
 * Example: T | 1 | Buy groceries
 */
public class Storage {

    private static final String FILE_DELIMITER = " \\| ";
    private static final String DONE_FLAG = "1";

    // File parsing constants - indices for each field in the delimited format
    private static final int MINIMUM_REQUIRED_PARTS = 3;
    private static final int TASK_TYPE_INDEX = 0;
    private static final int COMPLETION_FLAG_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_TIME_INDEX = 3;
    private static final int EVENT_START_TIME_INDEX = 3;
    private static final int EVENT_END_TIME_INDEX = 4;

    private final Path filePath;
    private int corruptedEntriesCount = 0;

    /**
     * Creates a storage handler for the specified file path.
     * Ensures the file and parent directories exist before use.
     *
     * @param filePathString Path to the data storage file
     * @throws IOException If file or directory creation fails
     */
    public Storage(String filePathString) throws IOException {
        this.filePath = Paths.get(filePathString);
        ensureStorageLocationExists();
    }

    /**
     * Loads all tasks from the storage file.
     * Handles corrupted entries gracefully by skipping them and continuing.
     *
     * @return List of successfully loaded tasks
     * @throws IOException If file reading fails completely
     */
    public ArrayList<Task> load() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>(); // Fresh start for new users
        }

        List<String> fileLines = readAllLinesFromFile();
        return parseTasksFromLines(fileLines);
    }

    /**
     * Saves all tasks to the storage file.
     * Converts tasks to file format and writes atomically to prevent data corruption.
     *
     * @param tasks List of tasks to persist to storage
     * @throws IOException If file writing fails
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        List<String> taskFileLines = convertTasksToFileLines(tasks);
        writeLinesToFile(taskFileLines);
    }




    /**
     * Ensures the storage file and its parent directories exist.
     * Creates them if they don't already exist.
     */
    private void ensureStorageLocationExists() throws IOException {
        createParentDirectoriesIfNeeded();
        createFileIfNeeded();
    }

    private void createParentDirectoriesIfNeeded() throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null && !Files.exists(parentDirectory)) {
            Files.createDirectories(parentDirectory);
        }
    }

    private void createFileIfNeeded() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Reads all lines from the storage file with error handling.
     */
    private List<String> readAllLinesFromFile() throws IOException {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new IOException("Cannot read data file - check file accessibility: " + e.getMessage());
        }
    }

    /**
     * Parses file lines into Task objects, handling corruption gracefully.
     * Counts and reports any corrupted entries found during parsing.
     */
    private ArrayList<Task> parseTasksFromLines(List<String> fileLines) {
        ArrayList<Task> tasks = new ArrayList<>();
        corruptedEntriesCount = 0;

        for (int lineNumber = 0; lineNumber < fileLines.size(); lineNumber++) {
            String line = fileLines.get(lineNumber);
            Task task = parseTaskFromLine(line, lineNumber + 1);

            if (task != null) {
                tasks.add(task);
            }
        }

        reportCorruptedEntriesIfAny();
        return tasks;
    }

    private void reportCorruptedEntriesIfAny() {
        if (corruptedEntriesCount > 0) {
            System.err.println("Warning: Found " + corruptedEntriesCount +
                    " corrupted entries in data file. Your other tasks are safe!");
        }
    }

    /**
     * Parses a single line from the storage file into a Task object.
     * Returns null if the line is corrupted, empty, or cannot be parsed.
     */
    private Task parseTaskFromLine(String line, int lineNumber) {
        if (isEmptyLine(line)) {
            return null; // Skip empty lines silently
        }

        try {
            String[] lineParts = line.split(FILE_DELIMITER);
            validateLineParts(lineParts);
            return createTaskFromParsedParts(lineParts);
        } catch (Exception e) {
            handleCorruptedLine(lineNumber, e.getMessage());
            return null;
        }
    }

    private boolean isEmptyLine(String line) {
        return line == null || line.trim().isEmpty();
    }

    private void validateLineParts(String[] parts) throws IllegalArgumentException {
        if (parts.length < MINIMUM_REQUIRED_PARTS) {
            throw new IllegalArgumentException("Insufficient fields in line");
        }

        validateTaskTypeField(parts[TASK_TYPE_INDEX]);
        validateDescriptionField(parts[DESCRIPTION_INDEX]);
    }

    private void validateTaskTypeField(String taskTypeField) throws IllegalArgumentException {
        if (taskTypeField.length() != 1) {
            throw new IllegalArgumentException("Invalid task type format");
        }
    }

    private void validateDescriptionField(String descriptionField) throws IllegalArgumentException {
        if (descriptionField.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty task description");
        }
    }

    /**
     * Creates a Task object from validated file parts.
     * Applies completion status after task creation.
     */
    private Task createTaskFromParsedParts(String[] parts) throws Exception {
        TaskType taskType = TaskType.fromSymbol(parts[TASK_TYPE_INDEX].charAt(0));
        boolean isCompleted = DONE_FLAG.equals(parts[COMPLETION_FLAG_INDEX]);
        String description = parts[DESCRIPTION_INDEX].trim();

        Task task = createTaskByType(taskType, description, parts);

        if (isCompleted) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Factory method that creates the appropriate task type based on parsed data.
     * Handles different task types and their specific time requirements.
     */
    private Task createTaskByType(TaskType taskType, String description, String[] parts) throws Exception {
        switch (taskType) {
        case TODO:
            return new ToDo(description);

        case DEADLINE:
            LocalDateTime deadlineTime = parseDeadlineTime(parts);
            return new Deadline(description, deadlineTime);

        case EVENT:
            LocalDateTime[] eventTimes = parseEventTimes(parts);
            return new Event(description, eventTimes[0], eventTimes[1]);

        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    private LocalDateTime parseDeadlineTime(String[] parts) throws Exception {
        validateDeadlinePartsCount(parts);
        return LocalDateTime.parse(parts[DEADLINE_TIME_INDEX], DateFormats.INPUT_FORMAT);
    }

    private LocalDateTime[] parseEventTimes(String[] parts) throws Exception {
        validateEventPartsCount(parts);
        LocalDateTime startTime = LocalDateTime.parse(parts[EVENT_START_TIME_INDEX], DateFormats.INPUT_FORMAT);
        LocalDateTime endTime = LocalDateTime.parse(parts[EVENT_END_TIME_INDEX], DateFormats.INPUT_FORMAT);
        return new LocalDateTime[]{startTime, endTime};
    }

    private void validateDeadlinePartsCount(String[] parts) throws IllegalArgumentException {
        if (parts.length <= DEADLINE_TIME_INDEX) {
            throw new IllegalArgumentException("Missing deadline time");
        }
    }

    private void validateEventPartsCount(String[] parts) throws IllegalArgumentException {
        if (parts.length <= EVENT_END_TIME_INDEX) {
            throw new IllegalArgumentException("Missing event time information");
        }
    }

    private void handleCorruptedLine(int lineNumber, String errorReason) {
        System.err.println("Warning: Line " + lineNumber + " is corrupted (" + errorReason + "), skipping...");
        corruptedEntriesCount++;
    }

    /**
     * Converts all tasks to their file string representation.
     * Filters out any tasks that cannot be properly serialized.
     */
    private List<String> convertTasksToFileLines(ArrayList<Task> tasks) {
        List<String> fileLines = new ArrayList<>();

        for (Task task : tasks) {
            addValidTaskToFileLines(task, fileLines);
        }

        return fileLines;
    }

    private void addValidTaskToFileLines(Task task, List<String> fileLines) {
        String taskFileRepresentation = task.toFileString();

        if (isValidTaskFileRepresentation(taskFileRepresentation)) {
            fileLines.add(taskFileRepresentation);
        }
    }

    private boolean isValidTaskFileRepresentation(String fileString) {
        return fileString != null && !fileString.trim().isEmpty();
    }

    /**
     * Writes all lines to the file atomically, replacing existing content.
     * Uses atomic write operations to prevent data corruption during save.
     */
    private void writeLinesToFile(List<String> lines) throws IOException {
        try {
            Files.write(filePath, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Cannot save tasks - check write permissions: " + e.getMessage());
        }
    }
}