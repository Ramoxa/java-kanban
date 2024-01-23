package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected static HistoryManager historyManager;
    protected int createId;


    public InMemoryTaskManager (HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.addTask(task);
        }
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        if (tasks.get(id) != null) {
            historyManager.addTask(tasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (tasks.get(id) != null) {
            historyManager.addTask(tasks.get(id));
        }
        return epics.get(id);
    }

    @Override
    public List<Task> getTasks() {
        List<Task> keyTask = new ArrayList<>(tasks.values());
        return keyTask;
    }

    @Override
    public List<Epic> getEpics() {
        List<Epic> keyEpic = new ArrayList<>(epics.values());
        return keyEpic;
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> keySubtasks = new ArrayList<>(subtasks.values());
        return keySubtasks;
    }

    @Override
    public Task createTask(Task task) {
        task.setStatus(Status.NEW);
        task.setId(createId++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Subtask createSubTask(Subtask subtask) {
        subtask.setStatus(Status.NEW);
        subtask.setId(createId++);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setStatus(Status.NEW);
        epic.setId(createId++);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        long id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.replace(task.getId(), task);
        System.out.println("Обновлено");
    }

    @Override
    public void updateEpic(Epic epic) {
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.replace(epic.getId(), epic);
        System.out.println("Обновлено");
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        long id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.replace(subtask.getId(), subtask);
        System.out.println("Обновлено");
    }

    @Override
    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        subtask.setStatus(Status.NEW);
        epic.getIDsOfSubtasks(subtask.getId()).add(subtask.getId());
        subtask.setEpicID(epic.getId());
        return epic;
    }

    @Override
    public Subtask deleteSubtask(Subtask subtask) {
        subtask.setStatus(Status.DONE);
        subtasks.remove(subtask.getId(), subtask);
        return subtask;
    }

    @Override
    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        deleteSubtask(subtask);
        epic.getIDsOfSubtasks(subtask.getId()).remove(subtask.getId());
        if (epic.getIDsOfSubtasks(subtask.getId()).isEmpty()) {
            epic.setStatus(Status.DONE);
        }
        if (epic.getStatus() == Status.DONE) {
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    @Override
    public Task deleteTask(Task task) {
        tasks.remove(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}