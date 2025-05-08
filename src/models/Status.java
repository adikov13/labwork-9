package models;

public enum Status {
    NEW("Новая"),
    IN_PROGRESS("В работе"),
    DONE("Выполнена"),
    OVERDUE("Просрочена");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}