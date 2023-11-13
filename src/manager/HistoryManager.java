package manager;

import inside.Task;

import java.util.List;

public interface HistoryManager {
    public List <Task> getHistory ();

    public void addTask (Task task);
}
