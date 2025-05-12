package models;

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
        addApp(tasks);
    }

    private static void menuUser(){

    }

    private static void addApp(List<Task> task){
        String addTitle = addTitle();
        String addDescription = addDescription();
        LocalDate addDate= addCompletionDate();
        Priority newPriority = addPriority();
        task.add(new Task(addTitle, addDescription, addDate ,newPriority));
        System.out.println(task);
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
                System.out.printf("Вы выбрали %s%n", Priority.LOW);
                priority=  Priority.LOW;
                break;
            case 2:
                System.out.printf("Вы выбрали %s%n", Priority.MEDIUM);
                priority = Priority.MEDIUM;
                break;
            case 3:
                System.out.printf("Вы выбрали %s%n", Priority.HIGH);
                priority = Priority.HIGH;
                break;
            default:
                System.out.println("Нет такой команды");
        }
        return priority;
    }

    private static String addTitle(){
        String newTitle = "";
        while (newTitle.isBlank()){
            System.out.println("Введите название задачи");
            newTitle = sc.nextLine();
            if(newTitle.isBlank()){
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
        LocalDate completion = null;
        while (newDate.isBlank()) {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            newDate = sc.nextLine();

            if (newDate.isBlank()) {
                System.out.println("Данный не подходит давай по проверяющий");
            } else {
                try {
                    completion = LocalDate.parse(newDate, dateCompletionDate);
                } catch (DateTimeException e) {
                    System.out.println("Неверный формат даты: 22.07.2023");
                }
            }

        }
        return completion;
    }
}
