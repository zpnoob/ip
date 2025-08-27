import java.lang.reflect.Array;
import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final Path filePath;

    public Storage(String filePathStr) {
        this.filePath = Paths.get(filePathStr);
    }

    //load tasks from file and return the arraylist of task last saved
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if(!Files.exists(filePath)) {
            //if it doesnt exist, create a folder and file for it on first run
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            // return empty list at first run
            return tasks;
        }

        //open text file located at filePath
        BufferedReader reader = Files.newBufferedReader(filePath);
        String line;
        while ((line = reader.readLine()) != null) {
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
                Task deadline = new Deadline(parts[2], parts[3]);
                if (parts[1].equals("1")) {
                    deadline.markAsDone();
                }
                tasks.add(deadline);
                break;
            case "E":
                Task event = new Event(parts[2], parts[3], parts.length > 4 ? parts[4] : "");
                if (parts[1].equals("1")) {
                    event.markAsDone();
                }
                tasks.add(event);
                break;
            }
        }
        reader.close();
        return tasks;
    }

    // save the tasks back into the file
    public void save(ArrayList<Task> tasks) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(filePath);
        // Open the file at filePath, overwrite content if file exists or create file if it doesn't
        for (Task task : tasks) {
            // Method for writing each task back into the format to be saved
            writer.write(task.toFileString());
            writer.newLine();
        }
        writer.close();
    }

}
