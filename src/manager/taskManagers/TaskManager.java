package manager.taskManagers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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

    void deleteSubtask(int id);

    void deleteTask(int id);

    List<Task> getHistory();

    List<Epic> getListAllEpic();

    ArrayList<Subtask> getListSubTasks();

    List<Task> getListAllTasks();

    List<Epic> getEpicsByStatus(Status status);


    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    Set<Task> getPrioritizedTasks();

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();
}