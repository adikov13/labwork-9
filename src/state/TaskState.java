package state;

import models.Status;
import models.Task;

public interface TaskState {
        String getName();

        void changeState(Task task, String newState);
        void editDescription(Task task, String newDesc);
        boolean canDelete();





}


