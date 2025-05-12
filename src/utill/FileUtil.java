package utill;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

    public class utill {
        private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

        private FileUtil() {
        }

        public static Tasks[] readTaskFile() {
            try {
                String json = Files.readString(Paths.get("src/data/tasks.json"));
                return (Tasks[])GSON.fromJson(json, Tasks[].class);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.out.println(ioe.getMessage());
                return new Tasks[0];
            }
        }

    }
}
