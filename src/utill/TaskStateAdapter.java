package util;

import com.google.gson.*;
import state.*;
import java.lang.reflect.Type;

public class TaskStateAdapter implements JsonSerializer<TaskState>, JsonDeserializer<TaskState>{
    @Override
    public JsonElement serialize(TaskState state, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(state.getClass().getSimpleName());
    }

    @Override
    public TaskState deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonObject()) {
            return new NewState();
        }

        String stateName = json.getAsString();
        switch (stateName) {
            case "NewState": return new NewState();
            case "InProgressState": return new InProgressState();
            case "DoneState": return new DoneState();
            case "OverdueState": return new OverdueState();
            default: return new NewState();
        }
    }
}