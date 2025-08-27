public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("Bye. I hope I never see you again..");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
