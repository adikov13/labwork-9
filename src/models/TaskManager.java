package models;

import state.TaskState;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static Scanner sc = new Scanner(System.in);

    public static void toDoAppRun() {
        List<Task> tasks = new ArrayList<>();
        menuUser(tasks);
    }

    private static void menuUser(List<Task> task) {
        while (true) {
            System.out.println("Выберите команду");
            System.out.println("1 - Посмотреть все задачи");
            System.out.println("2 - Добавить задачу");
            System.out.println("3 - Выбрать задачу");
            System.out.println("4 - Список сортировки");
            System.out.println("0 - Выход");
            int chooseCommand = sc.nextInt();
            sc.nextLine();
            switch (chooseCommand) {
                case 1:
                    printTask(task);
                    break;
                case 2:
                    addApp(task);
                    break;
                case 3:
                    choseApp(task);
                    break;
                case 4:
                    sortTasks(task);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Нет такой команды");
            }

        }
    }


    private static void printTask(List<Task> task) {
        if (task.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            int i = 1;
            System.out.println("----+--------+-------+--------+----------+-------+---------------+");
            for (Task t : task) {
                System.out.printf("|Задача №%d |%n|Название: %3s|%n|Описание: %3s|%n|Дата Создания: %3s||Дата Завершения: %3s|%n|Приоритет: %3s|Статус: %4s |%n",
                        i++, t.getTitle(), t.getDescription(), t.getCreatedDate(), t.getCompletionDate(),
                        t.getPriority(), t.getState());
            }
            System.out.println("----+--------+-------+--------+----------+-------+---------------+");
        }

    }

    private static void choseApp(List<Task> task) {
        printTask(task);
        int indexApp = task.size();
        while (indexApp >= task.size() || indexApp < 1) {
            System.out.printf("Введите номер задачи (1-%s)%n", task.size());
            try {
                indexApp = sc.nextInt();
                sc.nextLine();
                if (indexApp > task.size() || indexApp < 1) {
                    System.out.println("Данный не подходит давай по новой");
                } else {
                    Task t = task.get(indexApp - 1);
                    addStatus(t, task);
                }
            } catch (Exception e) {
                System.out.println("Введите число");
                sc.nextLine();
            }
        }
    }


    private static void addApp(List<Task> task) {
        String appTitle = addTitle();
        String appDescription = addDescription();
        LocalDate appDate = addCompletionDate();
        Priority appPriority = addPriority();
        task.add(new Task(appTitle, appDescription, appDate, appPriority));
        System.out.println(task);
    }


    private static String addTitle() {
        String newTitle = "";
        while (newTitle.isEmpty()) {
            System.out.println("Введите название задачи");
            newTitle = sc.nextLine();
            if (newTitle.isEmpty()) {
                System.out.println("Данный не подходит давай по проверяющий");
            }
        }
        return newTitle;
    }

    private static String addDescription() {
        String newDescription = "";
        while (newDescription.isBlank()) {
            System.out.println("Введите описание задачи");
            newDescription = sc.nextLine();
            if (newDescription.isBlank()) {
                System.out.println("Данный не подходит давай по проверяющий");
            }
        }
        return newDescription;
    }

    private static LocalDate addCompletionDate() {
        DateTimeFormatter dateCompletionDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String newDate = "";
        while (true) {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            newDate = sc.nextLine();
            if (newDate.isBlank()) {
                System.out.println("Данный не подходит давай по проверяющий");
            } else {
                try {
                    return LocalDate.parse(newDate, dateCompletionDate);
                } catch (DateTimeException e) {
                    System.out.println("Неверный формат даты: 22.07.2023");
                }
            }
        }
    }

    private static Priority addPriority() {
        while (true) {
            System.out.println("Выберите приоритет");
            System.out.println("1 - Низкий");
            System.out.println("2 - Средний");
            System.out.println("3 - Высокий");
            int choosePriority = sc.nextInt();
            switch (choosePriority) {
                case 1:
                    System.out.printf("Вы выбрали %s приоритет%n", Priority.LOW);
                    return Priority.LOW;
                case 2:
                    System.out.printf("Вы выбрали %s приоритет%n", Priority.MEDIUM);
                    return Priority.MEDIUM;
                case 3:
                    System.out.printf("Вы выбрали %s приоритет%n", Priority.HIGH);
                    return Priority.HIGH;
                default:
                    System.out.println("Нет такого приоритета");
            }
        }

    }

    private static void addStatus(Task task, List<Task> tasks) {

        TaskState st = task.getState();
        System.out.printf("|Название: %3s|%n|Описание: %3s|%n|Дата Создания: %3s||Дата Завершения: %3s|%n|Приоритет: %3s|Статус: %4s |%n",
                task.getTitle(), task.getDescription(), task.getCreatedDate(), task.getCompletionDate(),
                task.getPriority(), task.getState());
        boolean appStatus = true;
        while (appStatus) {
            int choose;
            switch (st.getName()) {
                case "NEW":
                    System.out.println("1 - Изменит на статус В работе");
                    System.out.println("2 - Изменить описание");
                    System.out.println("3 - Удаление задачи");
                    System.out.println("0-выход");
                    choose = sc.nextInt();
                    sc.nextLine();
                    switch (choose) {
                        case 1:
                            task.changeStatus(Status.IN_PROGRESS.name());
                            appStatus = false;
                            break;
                        case 2:
                            task.editDescription(addDescription());
                            break;
                        case 3:
                            if (task.canDelete()) {
                                tasks.remove(task);
                                System.out.printf("Вы удалили задачу %s%n", task.getTitle());
                                appStatus = false;
                                break;
                            } else {
                                System.out.println("Задачу удалить нельзя");
                                break;
                            }
                        case 0:
                            appStatus = false;
                            break;
                        default:
                            System.out.println("Нет такой команды");
                    }
                    break;
                case "In_PROGRESS":
                    System.out.println("1 - Изменит на статус Выполнена");
                    System.out.println("2 - Изменить описание");
                    System.out.println("0-выход");
                    choose = sc.nextInt();
                    sc.nextLine();
                    switch (choose) {
                        case 1:
                            task.changeStatus(Status.IN_PROGRESS.name());
                            appStatus = false;
                            break;
                        case 0:
                            appStatus = false;
                            break;
                        default:
                            System.out.println("Нет такой команды");
                    }
                    break;
                case "DONE":
                    System.out.println("1 - Задачу завершена удалять нельзя");
                    System.out.println("0-выход");
                    choose = sc.nextInt();
                    sc.nextLine();
                    if (choose == 0) {
                        appStatus = false;
                        break;
                    } else {
                        System.out.println("Нет такой команды");
                    }
                    return;
            }
        }
    }

    public static void sortTasks(List<Task> tasks){
        System.out.println("1 - По приоритету");
        System.out.println("2 - По описанию");
        System.out.println("3 - По дате создания");
        System.out.println("0-выход");
        int choose = sc.nextInt();
        sc.nextLine();
        switch (choose){
            case 1:
                System.out.println("По приоритету");
                tasks.sort(Comparator.comparing(Task::getPriority).reversed());
                printTask(tasks);
                break;
            case 2:
                System.out.println("По описанию");
                tasks.sort(Comparator.comparing(Task::getDescription).reversed());
                printTask(tasks);
                break;
            case 3:
                System.out.println("По дате создания");
                tasks.sort(Comparator.comparing(Task::getCreatedDate).reversed());
                printTask(tasks);
                break;
        }
    }
}
