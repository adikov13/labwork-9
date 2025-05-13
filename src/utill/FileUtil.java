package util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Task;
import state.TaskState;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(TaskState.class, new TaskStateAdapter())
            .setPrettyPrinting()
            .create();
    private static final String FILE_PATH = "tasks.json";

    public static List<Task> loadTasks() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                String json = Files.readString(path);

                json = json.replace("\"state\": {}", "\"state\": \"NewState\"");

                List<Task> tasks = GSON.fromJson(json, new TypeToken<List<Task>>(){}.getType());
                return tasks != null ? tasks : new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке задач: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void saveTasks(List<Task> tasks) {
        try {
            String json = GSON.toJson(tasks);
            Files.writeString(Paths.get(FILE_PATH), json);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении задач: " + e.getMessage());
        }
    }
}