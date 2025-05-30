package state;

import models.Task;

public class InProgressState implements TaskState {
    @Override
    public String getName() {
        return "IN_PROGRESS";
    }

    @Override
    public void changeState(Task task, String newState) {
        if ("DONE".equalsIgnoreCase(newState)) {
            task.setState(new DoneState());
            System.out.println("Статус изменен на 'Выполнена'");
        } else {
            System.out.println("Из статуса IN_PROGRESS можно перейти только в DONE");
        }
    }

    @Override
    public void editDescription(Task task, String newDesc) {
        System.out.println("Нельзя изменить описание задачи в состоянии IN_PROGRESS");
    }

    @Override
    public boolean canDelete() {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}