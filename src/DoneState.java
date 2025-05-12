public class DoneState implements TaskState {

    @Override
    public String getName() {
        return "DONE";
    }

    @Override
    public TaskState nextState() {
        return this;
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
