package manager;

import manager.historyManagers.HistoryManager;
import manager.historyManagers.InMemoryHistoryManager;
import manager.taskManagers.HttpTaskManager;

public class Managers {
    public static HttpTaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}

