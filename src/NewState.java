public class NewState implements TaskState {

    @Override
    public String getName() {
        return "NEW";
    }

    @Override
    public TaskState nextState() {
        return new InProgressState();
    }

    @Override
    public boolean canEditDescription() {
        return true;
    }

    @Override
    public boolean canDelete() {
        return true;
    }
}
