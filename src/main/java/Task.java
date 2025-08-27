public abstract class Task {
    private String description;
    private boolean isDone;
    private char taskSymbol;
    //this field

    public Task(String description, char taskSymbol) {
        this.description = description;
        this.isDone = false;
        this.taskSymbol = taskSymbol;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); //marks done task with X
    }

    //next 2 methods are to change the boolean for whether a task is done
    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    public char getTaskSymbol() {
        return this.taskSymbol;
    }

    public abstract String toFileString();

    //need to add a toString to represent [X] or [ ]
    @Override
    public String toString() {
        return "[" + getTaskSymbol() + "][" + getStatusIcon() + "] " + description;
    }
}
