package manager;

import loadManager.FileBackedTasksManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTasksManager(new File("data"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
