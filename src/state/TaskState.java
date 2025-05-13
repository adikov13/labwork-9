package state;

import models.Task;

public interface TaskState {
        String getName();
        void changeState(Task task, String newState);
        void editDescription(Task task, String newDesc);
        boolean canDelete();

        default String getSimpleName() {
                return this.getClass().getSimpleName();
        }
}