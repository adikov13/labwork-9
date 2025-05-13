package models;

import state.NewState;
import state.TaskState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private LocalDate completionDate;
    private final LocalDate createdDate;
    private Priority priority;
    private TaskState state;
    private Integer rating;

    public Task(String title, String description, LocalDate completionDate, Priority priority) {
        this.title = Objects.requireNonNull(title, "Название задачи не может быть null");
        this.description = description;
        this.completionDate = completionDate;
        this.createdDate = LocalDate.now();
        this.priority = priority;
        this.state = new NewState();
        this.rating = null;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public LocalDate getCreatedDate() { return createdDate; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public TaskState getState() {
        return state;
    }
    public void setState(TaskState state) { this.state = state; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public void changeStatus(String newState) { state.changeState(this, newState); }
    public void editDescription(String newDescription) { state.editDescription(this, newDescription); }
    public boolean canDelete() { return state.canDelete(); }
    public boolean isOverdue() { return LocalDate.now().isAfter(completionDate); }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String overdueMark = isOverdue() ? "*ПРОСРОЧЕНО* " : "";
        String ratingStr = (rating != null) ? " Оценка: " + rating + "/10" : "";
        return String.format("%s[%s] %s - %s (Создана: %s, Завершить до: %s, Статус: %s%s)",
                overdueMark, priority, title, description,
                createdDate.format(formatter),
                completionDate.format(formatter), state, ratingStr);
    }

    public void checkOverdue() {
        if (isOverdue() && !getState().getName().equals("DONE")) {
            setState(new state.OverdueState());
        }
    }

    public String getStateAsString() {
        return state.getSimpleName();
    }
}