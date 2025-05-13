package state;

import models.Task;

public class NewState implements TaskState {

    @Override
    public String getName() {
        return "NEW";
    }

    @Override
    public void changeState(Task task, String newState) {
        if ("in_progress".equalsIgnoreCase(newState)) {
            task.setState(new InProgressState());
            System.out.println("Статус изменён на IN PROGRESS");
        } else {
            System.out.println("Из статуса NEW можно перейти только в IN PROGRESS");
        }
    }


    @Override
    public void editDescription(Task task, String newDesc) {
        task.setDescription(newDesc);
        System.out.println("Описание обновлено");
    }

    @Override
    public boolean canDelete() {
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
