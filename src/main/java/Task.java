public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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
        return isDone;
    }

    //need to add a toString to represent [X] or [ ]
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

}
