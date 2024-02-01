package manager;

import manager.historyManagers.HistoryManager;
import manager.historyManagers.InMemoryHistoryManager;
import manager.taskManagers.InMemoryTaskManager;
import manager.taskManagers.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
