package state;

import models.Task;

public class OverdueState implements TaskState {
    @Override
    public String getName() {
        return "OVERDUE";
    }

    @Override
    public void changeState(Task task, String newState) {
        System.out.println("Просроченную задачу можно только завершить");
        if ("DONE".equalsIgnoreCase(newState)) {
            task.setState(new DoneState());
        }
    }

    @Override
    public void editDescription(Task task, String newDesc) {
        System.out.println("Нельзя изменить описание просроченной задачи");
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