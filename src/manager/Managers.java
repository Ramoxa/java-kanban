package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.historyManagers.HistoryManager;
import manager.historyManagers.InMemoryHistoryManager;
import manager.taskManagers.HttpTaskManager;
import manager.taskManagers.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}

