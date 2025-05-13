package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import state.NewState;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskManager {
    private static final String FILE_PATH = "tasks.json";
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .setPrettyPrinting()
            .create();
    private static Scanner sc = new Scanner(System.in);

    public static void toDoAppRun() {
        List<Task> tasks = util.FileUtil.loadTasks();
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        menuUser(tasks);
    }

    private static List<Task> loadTasks() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                String json = Files.readString(path);
                return GSON.fromJson(json, new TypeToken<List<Task>>(){}.getType());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке задач: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    private static void saveTasks(List<Task> tasks) {
        try {
            String json = GSON.toJson(tasks);
            Files.writeString(Paths.get(FILE_PATH), json);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении задач: " + e.getMessage());
        }
    }

    private static void menuUser(List<Task> tasks) {
        while (true) {
            System.out.println("\n=== Меню управления задачами ===");
            System.out.println("1 - Показать все задачи");
            System.out.println("2 - Добавить задачу");
            System.out.println("3 - Выбрать задачу");
            System.out.println("4 - Сортировка задач");
            System.out.println("5 - Фильтрация задач");
            System.out.println("6 - Поиск задач");
            System.out.println("0 - Выход");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> printTasks(tasks);
                    case 2 -> addTask(tasks);
                    case 3 -> selectTask(tasks);
                    case 4 -> sortTasks(tasks);
                    case 5 -> filterTasks(tasks);
                    case 6 -> searchTasks(tasks);
                    case 0 -> {
                        saveTasks(tasks);
                        return;
                    }
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    private static void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }

        System.out.println("\n=== Список задач ===");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i));
        }
    }

    private static void addTask(List<Task> tasks) {
        System.out.println("\n=== Добавление новой задачи ===");
        String title = inputTitle();
        String description = inputDescription();
        LocalDate completionDate = inputCompletionDate();
        Priority priority = inputPriority();

        Task newTask = new Task(title, description, completionDate, priority);
        newTask.setState(new NewState());
        tasks.add(newTask);
        saveTasks(tasks);
        System.out.println("Задача успешно добавлена!");
    }
    private static String inputTitle() {
        while (true) {
            System.out.print("Введите название задачи: ");
            String title = sc.nextLine().trim();
            if (!title.isEmpty()) return title;
            System.out.println("Название не может быть пустым!");
        }
    }

    private static String inputDescription() {
        System.out.print("Введите описание задачи: ");
        return sc.nextLine().trim();
    }

    private static LocalDate inputCompletionDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (true) {
            System.out.print("Введите дату завершения (dd.MM.yyyy): ");
            try {
                return LocalDate.parse(sc.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты! Пример: 31.12.2023");
            }
        }
    }

    private static Priority inputPriority() {
        while (true) {
            System.out.println("Выберите приоритет:");
            System.out.println("1 - Низкий");
            System.out.println("2 - Средний");
            System.out.println("3 - Высокий");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> { return Priority.LOW; }
                    case 2 -> { return Priority.MEDIUM; }
                    case 3 -> { return Priority.HIGH; }
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    private static void selectTask(List<Task> tasks) {
        printTasks(tasks);
        if (tasks.isEmpty()) return;

        try {
            System.out.print("Выберите номер задачи: ");
            int index = Integer.parseInt(sc.nextLine()) - 1;
            if (index >= 0 && index < tasks.size()) {
                taskMenu(tasks.get(index), tasks);
            } else {
                System.out.println("Неверный номер задачи!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите число!");
        }
    }

    private static void taskMenu(Task task, List<Task> tasks) {
        while (true) {
            System.out.println("\n=== Управление задачей ===");
            System.out.println(task); // Будет использовать toString()
            System.out.println("1 - Изменить статус");
            System.out.println("2 - Изменить описание");
            System.out.println("3 - Удалить задачу");
            if ("DONE".equals(task.getState().getName()) && task.getRating() == null) {
                System.out.println("4 - Поставить оценку");
            }
            System.out.println("0 - Назад");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> changeTaskStatus(task);
                    case 2 -> editTaskDescription(task);
                    case 3 -> {
                        if (deleteTask(task, tasks)) return;
                    }
                    case 4 -> {
                        if ("DONE".equals(task.getState().getName())) {
                            rateTask(task);
                        }
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Неверный выбор!");
                }
                saveTasks(tasks);
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    private static void changeTaskStatus(Task task) {
        System.out.println("\nТекущий статус: " + task.getState());
        System.out.println("Доступные действия:");

        switch (task.getState().getName()) {
            case "NEW":
                System.out.println("1 - Перевести в работу (IN_PROGRESS)");
                System.out.print("Выберите действие: ");
                if (sc.nextLine().equals("1")) {
                    task.changeStatus("IN_PROGRESS");
                }
                break;

            case "IN_PROGRESS":
                System.out.println("1 - Завершить задачу (DONE)");
                System.out.print("Выберите действие: ");
                if (sc.nextLine().equals("1")) {
                    task.changeStatus("DONE");
                }
                break;

            default:
                System.out.println("Для этого статуса нет доступных действий");
        }
    }

    private static void editTaskDescription(Task task) {
        if (task.getState().getName().equals("NEW")) {
            System.out.print("Введите новое описание: ");
            task.editDescription(sc.nextLine());
            System.out.println("Описание обновлено!");
        } else {
            System.out.println("Редактирование описания недоступно для текущего статуса!");
        }
    }

    private static boolean deleteTask(Task task, List<Task> tasks) {
        if (task.canDelete()) {
            tasks.remove(task);
            System.out.println("Задача удалена!");
            return true;
        } else {
            System.out.println("Нельзя удалить задачу с текущим статусом!");
            return false;
        }
    }

    private static void rateTask(Task task) {
        if (task.getRating() == null) {
            try {
                System.out.print("Введите оценку (1-10): ");
                int rating = Integer.parseInt(sc.nextLine());
                if (rating >= 1 && rating <= 10) {
                    task.setRating(rating);
                    System.out.println("Оценка сохранена!");
                } else {
                    System.out.println("Оценка должна быть от 1 до 10!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        } else {
            System.out.println("Оценка уже поставлена!");
        }
    }

    private static void sortTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст!");
            return;
        }

        System.out.println("\n=== Сортировка задач ===");
        System.out.println("1 - По приоритету (высокий -> низкий)");
        System.out.println("2 - По дате создания (новые -> старые)");
        System.out.println("3 - По дате завершения (ближайшие -> дальние)");
        System.out.println("4 - По названию (А-Я)");
        System.out.println("0 - Назад");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    tasks.sort(Comparator.comparing(Task::getPriority).reversed());
                    System.out.println("Задачи отсортированы по приоритету!");
                }
                case 2 -> {
                    tasks.sort(Comparator.comparing(Task::getCreatedDate).reversed());
                    System.out.println("Задачи отсортированы по дате создания!");
                }
                case 3 -> {
                    tasks.sort(Comparator.comparing(Task::getCompletionDate));
                    System.out.println("Задачи отсортированы по дате завершения!");
                }
                case 4 -> {
                    tasks.sort(Comparator.comparing(Task::getTitle));
                    System.out.println("Задачи отсортированы по названию!");
                }
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор!");
            }
            printTasks(tasks);
        } catch (NumberFormatException e) {
            System.out.println("Введите число!");
        }
    }

    private static void filterTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст!");
            return;
        }

        System.out.println("\n=== Фильтрация задач ===");
        System.out.println("1 - По приоритету");
        System.out.println("2 - По статусу");
        System.out.println("3 - Просроченные");
        System.out.println("0 - Назад");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            List<Task> filtered = new ArrayList<>();

            switch (choice) {
                case 1 -> {
                    Priority p = inputPriority();
                    filtered = tasks.stream()
                            .filter(t -> t.getPriority() == p)
                            .collect(Collectors.toList());
                }
                case 2 -> {
                    System.out.println("Доступные статусы: NEW, IN_PROGRESS, DONE, OVERDUE");
                    System.out.print("Введите статус: ");
                    String status = sc.nextLine().toUpperCase();
                    filtered = tasks.stream()
                            .filter(t -> t.getState().getName().equals(status))
                            .collect(Collectors.toList());
                }
                case 3 -> {
                    filtered = tasks.stream()
                            .filter(Task::isOverdue)
                            .collect(Collectors.toList());
                }
                case 0 -> { return; }
                default -> {
                    System.out.println("Неверный выбор!");
                    return;
                }
            }

            if (filtered.isEmpty()) {
                System.out.println("Задачи не найдены!");
            } else {
                printTasks(filtered);
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите число!");
        }
    }

    private static void searchTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст!");
            return;
        }

        System.out.println("\n=== Поиск задач ===");
        System.out.print("Введите ключевое слово: ");
        String keyword = sc.nextLine().toLowerCase();

        List<Task> results = tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(keyword) ||
                        t.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("Задачи не найдены!");
        } else {
            printTasks(results);
        }
    }
}