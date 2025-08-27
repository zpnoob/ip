public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showSpace();
        System.out.println("Bye. I hope I never see you again..");
        ui.showSpace();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
