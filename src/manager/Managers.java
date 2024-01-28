package manager;

import java.io.File;

public class Managers {

    public static InMemoryTaskManager getDefault() {
        return new FileBackedTasksManager(getDefaultHistory(), new File("resources.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
