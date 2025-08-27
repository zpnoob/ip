public class Deadline extends Task{
    private String by; //field determining when to complete task by

    public Deadline(String description, String by) {
        super(description,  'D');
        this.by = by;
    }

    public String getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | "+ getDescription() + " | " + getBy();
    }
}
