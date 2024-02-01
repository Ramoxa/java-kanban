package manager.taskManagers;


import manager.exceptions.ManagerSaveException;
import manager.Managers;
import tasks.Status;
import manager.historyManagers.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class InMemoryTaskManager implements TaskManager {


    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public HistoryManager historyManager;
    protected int createId = 1;
    private Set<Task> prioritizedTasks;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    private int getNextID() {
        return ++createId;
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private void addToPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
        checkIntersections();

    }

    private void checkIntersections() {
        List<Task> prioritizedTasks = getPrioritizedTasks();
        for (int i = 1; i < prioritizedTasks.size(); i++) {
            Task prioritizedTask = prioritizedTasks.get(i);
            if (prioritizedTask.getStartTime().isBefore(prioritizedTasks.get(i - 1).getEndTime()))
                throw new ManagerSaveException("Пересечение между " + prioritizedTasks.get(i) + prioritizedTasks.get(i - 1));
        }
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
        int id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.replace(task.getId(), task);
        System.out.println("Обновлено");
    }

    @Override
    public void updateEpic(Epic epic) {
        int id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.replace(epic.getId(), epic);
        System.out.println("Обновлено");
    }


    @Override

    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;

        }
        subtasks.replace(subtask.getId(), subtask);
        System.out.println("Обновлено");
    }


    @Override
    public void deleteSubtask(int id) {
        subtasks.get(id).setStatus(Status.DONE);
        if (subtasks.containsKey(id)) {
            tasks.remove(id);
        }
    }


    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Epic> getListAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getListSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getListAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpicsByStatus(Status status) {
        List<Epic> epicsByStatus = new ArrayList<>();
        for (Epic epic : epics.values()) {
            if (epic.getStatus() == status) {
                epicsByStatus.add(epic);
            }
        }
        return epicsByStatus;
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.addTask(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.addTask(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.addTask(subtasks.get(id));
        return subtasks.get(id);

    }

    @Override
    public void load() {

    }
}