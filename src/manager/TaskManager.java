package manager;

import java.util.List;
import inside.Epic;
import inside.Task;
import inside.Subtask;


public interface TaskManager {

    Task getTask(int id);

    Subtask getgetSubtask(int id);

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




}