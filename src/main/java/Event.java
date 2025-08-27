import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;
    //these additional fields required under Events.

    public Event(String description, LocalDate from, LocalDate to) {
        super(description, 'E');
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) +
                " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + getFrom() +
                " | " + getTo();

    }
}
