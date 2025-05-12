public class InProgressState implements TaskState {

    @Override
    public String getName() {
        return "IN_PROGRESS";
    }

    @Override
    public TaskState nextState() {
        return new DoneState();
    }

    @Override
    public boolean canEditDescription() {
        return false;
    }

    @Override
    public boolean canDelete() {
        return false;
    }
}
