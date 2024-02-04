package manager.historyManagers;

import tasks.Task;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();

    void addTask(Task task);

    void remove(int id);
}