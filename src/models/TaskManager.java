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
        switch (choosePriority) {
            case 1:
                System.out.printf("Вы выбрали %s%n", Priority.LOW);
                return Priority.LOW;
            case 2:
                System.out.printf("Вы выбрали %s%n", Priority.MEDIUM);
                return Priority.MEDIUM;
            case 3:
                System.out.printf("Вы выбрали %s%n", Priority.HIGH);
                return Priority.HIGH;
            default:
                System.out.println("Нет такой команды");
        }
        return null;
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
        while (newDate.isBlank()) {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            newDate = sc.nextLine();

            if (newDate.isBlank()) {
                System.out.println("Данный не подходит давай по проверяющий");
            } else {
                try {
                    LocalDate date = LocalDate.parse(newDate, dateCompletionDate);
                } catch (DateTimeException e) {
                    System.out.println("Неверный формат даты");
                }
            }
        }
        return null;
    }
}
