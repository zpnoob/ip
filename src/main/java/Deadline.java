import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDate by; //field determining when to complete task by

    public Deadline(String description, LocalDate by) {
        super(description,  'D');
        this.by = by;
    }

    public LocalDate getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | "+ getDescription() + " | " + getBy();
    }
}
