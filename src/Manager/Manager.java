package Manager;

import java.util.HashMap;
import java.util.List;
import Inside.Epic;
import Inside.Task;
import Inside.Subtask;


public class Manager {

    private int createId;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public List <Task> getTasks() {
        return (List<Task>) tasks;
    }

    public List <Epic> getEpics() {
        return (List<Epic>) epics;
    }

    public List <Subtask> getSubtasks() {
        return (List<Subtask>)  subtasks;
    }

    public Task createTask(Task task) {
        task.setStatus(Status.NEW);
        task.setId(++createId);
        tasks.put(task.getId(), task);
        return task;
    }

    public Subtask createSubTask(Subtask subtask) {
        subtask.setStatus(Status.NEW);
        subtask.setId(++createId);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    public Epic createEpic(Epic epic) {
        epic.setStatus(Status.NEW);
        epic.setId(++createId);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public void updateTask(Task task) {
        long id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
        System.out.println("Обновлено");
    }

    public void updateEpic(Epic epic) {
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Обновлено");
    }

    public void updateSubtask(Subtask subtask) {
        long id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        System.out.println("Обновлено");
    }


    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        subtask.setStatus(Status.NEW);
        epic.getIDsOfSubtasks().add(subtask.getId());
        subtask.setEpicID(epic.getId());
        return epic;
    }

    private Subtask deleteSubtask(Subtask subtask) {
        subtask.setStatus(Status.DONE);
        subtasks.remove(subtask.getId(), subtask);
        return subtask;
    }

    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        deleteSubtask(subtask);
        epic.getIDsOfSubtasks().remove(subtask.getId());
        if (epic.getIDsOfSubtasks().isEmpty()) {
            epic.setStatus(Status.DONE);
        }
        if (epic.getStatus() == Status.DONE) {
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    public Task deleteTask(Task task) {
        tasks.remove(task.getId(), task);
        return task;
    }
}



