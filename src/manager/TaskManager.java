package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.util.List;


public interface TaskManager {

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    Task createTask(Task task);

    Subtask createSubTask(Subtask subtask);

    Epic createEpic(Epic epic);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    Epic addSubtaskToEpic(Epic epic, Subtask subtask);

    Subtask deleteSubtask(Subtask subtask);

    Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask);

    Task deleteTask(Task task);

    List<Task> getHistory();

    public List<Epic> getListAllEpic();

    public List<Subtask> getListSubTasks();

    public List<Task> getListAllTasks();


}