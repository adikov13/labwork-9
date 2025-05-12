package models;

import jdk.jshell.Snippet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static Scanner sc = new Scanner(System.in);
    public static void toDoAppRun(){
        List<Task> tasks = new ArrayList<>();
        menuUser(tasks);
    }

    private static void menuUser(List<Task> task){
        while (true){
            System.out.println("Выберите команду");
            System.out.println("1 - Посмотреть все задачи");
            System.out.println("2 - Добавить задачу");
            System.out.println("3 - Выбрать задачу");
            System.out.println("0 - Выход");
            int chooseCommand = sc.nextInt();
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
                case 0:
                    return;
                default:
                    System.out.println("Нет такой команды");
            }

        }
    }


    private static void printTask(List<Task> task) {
        int i = 1;
        System.out.println("----+--------+-------+--------+----------+-------+---------------+");
        for (Task t : task) {
            System.out.printf("|%d |%n|Название: %3s|%n|Описание: %3s|%n|Дата Создания: %3s||Дата Завершения: %3s|%n|Приоритет: %3s|Статус: %4s |%n",
                    i++, t.getTitle(), t.getDescription(),  t.getCreatedDate(), t.getCompletionDate(),
                     t.getPriority(), t.getState());
        }
        System.out.println("----+--------+-------+--------+----------+-------+---------------+");
    }

    private static void choseApp(List<Task> task){
        printTask(task);
        int indexApp = task.size();
        while (indexApp >= task.size() || indexApp < 1){
            System.out.printf("Введите номер задачи (1-%s)%n", task.size());
            try{
                indexApp = sc.nextInt();
                sc.nextLine();
                if(indexApp > task.size() || indexApp < 1){
                    System.out.println("Данный не подходит давай по новой");
                }else {
                    Task t = task.get(indexApp-1);
                    System.out.printf("|Название: %3s|%n|Описание: %3s|%n|Дата Создания: %3s||Дата Завершения: %3s|%n|Приоритет: %3s|Статус: %4s |%n",
                             t.getTitle(), t.getDescription(),  t.getCreatedDate(), t.getCompletionDate(),
                            t.getPriority(), t.getState());
                    Status newState = addStatus();
                    t.changeStatus(newState.name());
                    break;
                };
            } catch (Exception e) {
                System.out.println("Введите число");
                sc.nextLine();
            }
        }
    }


    private static void addApp(List<Task> task){
        String appTitle = addTitle();
        String appDescription = addDescription();
        LocalDate appDate= addCompletionDate();
        Priority appPriority = addPriority();
        task.add(new Task(appTitle, appDescription, appDate ,appPriority));
        System.out.println(task);
    }


    private static String addTitle(){
        String newTitle = "";
        while (newTitle.isEmpty()){
            System.out.println("Введите название задачи");
            newTitle = sc.nextLine();
            if(newTitle.isEmpty()){
                System.out.println("Данный не подходит давай по проверяющий");
            }
        }
        return newTitle;
    }

    private static String addDescription(){
        String newDescription = "";
        while (newDescription.isBlank()){
            System.out.println("Введите описание задачи");
            newDescription = sc.nextLine();
            if(newDescription.isBlank()){
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
        System.out.println("Выберите приоритет");
        System.out.println("1 - Низкий");
        System.out.println("2 - Средний");
        System.out.println("3 - Высокий");
        int choosePriority = sc.nextInt();
        Priority priority = null;
        switch (choosePriority) {
            case 1:
                System.out.printf("Вы выбрали %s приоритет%n", Priority.LOW);
                priority=  Priority.LOW;
                break;
            case 2:
                System.out.printf("Вы выбрали %s приоритет%n", Priority.MEDIUM);
                priority = Priority.MEDIUM;
                break;
            case 3:
                System.out.printf("Вы выбрали %s приоритет%n", Priority.HIGH);
                priority = Priority.HIGH;
                break;
            default:
                System.out.println("Нет такого приоритета");
        }
        return priority;
    }

    private static Status addStatus() {
        System.out.println("Выберите статус");
        System.out.println("1 - Новая");
        System.out.println("2 - В работе");
        System.out.println("3 - Выполнена");
        int chooseStatus = sc.nextInt();
        Status state = null;
        switch (chooseStatus) {
            case 1:
                System.out.printf("У вас %s задача %n", Status.NEW);
                state =  Status.NEW;
                break;
            case 2:
                System.out.printf("Ваша задача в %s%n", Status.IN_PROGRESS);
                state = Status.IN_PROGRESS;
                break;
            case 3:
                System.out.printf("Вы %s задачу%n",  Status.DONE);
                state = Status.DONE;
                break;
            default:
                System.out.println("Нет задачи");
        }
        return state;
    }
}
