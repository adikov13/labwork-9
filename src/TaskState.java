public interface TaskState {
        String getName();

        TaskState nextState();
        boolean canEditDescription();
        boolean canDelete();
    }


