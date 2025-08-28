import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;

public class Storage {
    private final Path filePath;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Storage(String filePathStr) throws IOException {
        this.filePath = Paths.get(filePathStr);
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
    }

    //load tasks from file and return the arraylist of task last saved
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        // reads all lines from the file into a list<String>, each string corresponds to one task saved before
        // then loop over each line/string and splits it up by |
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            //split each line into parts, where each line is expected to have values separated by |
            String[] parts = line.split(" \\| ");
            switch (parts[0]) {
            case "T":
                Task todo = new ToDo(parts[2]);
                if (parts[1].equals("1")) {
                    todo.markAsDone();
                }
                tasks.add(todo);
                break;
            case "D":
                LocalDateTime deadlineDate = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                Task deadline = new Deadline(parts[2], deadlineDate);
                if (parts[1].equals("1")) {
                    deadline.markAsDone();
                }
                tasks.add(deadline);
                break;
            case "E":
                LocalDateTime fromDate = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                LocalDateTime toDate = LocalDateTime.parse(parts[4], DATE_TIME_FORMATTER);
                Task event = new Event(parts[2], fromDate, toDate);
                if (parts[1].equals("1")) {
                    event.markAsDone();
                }
                tasks.add(event);
                break;
            }
        }
        return tasks;
    }

    // save the tasks back into the file
    public void save(ArrayList<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toFileString());
        }
        Files.write(filePath, lines);
        // write content of the lines list to file at filePath, replacing what is in there
    }

}
