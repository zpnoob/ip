package jung.task;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description, 'T');
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }
}