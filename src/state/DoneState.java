package state;

import models.Task;

public class DoneState implements TaskState {
    @Override
    public String getName() {
        return "DONE";
    }

    @Override
    public void changeState(Task task, String newState) {
        System.out.println("Задача уже завершена ,смена статуса невозможна");
    }

    @Override
    public void editDescription(Task task, String newDesc) {
        System.out.println("Нельзя изменить описание завершённой задачи");
    }

    @Override
    public boolean canDelete() {
        return false;
    }


}

