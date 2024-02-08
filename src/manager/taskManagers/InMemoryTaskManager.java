package manager.taskManagers;

import manager.Managers;
import manager.historyManagers.HistoryManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId));

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public HistoryManager historyManager;
    protected int createId = 0;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    private int getNextID() {
        return ++createId;
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
        return new ArrayList<>(tasks.values());
    }


    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }


    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }


    @Override
    public Task createTask(Task task) {
        task.setStatus(Status.NEW);
        int newTaskId = getNextID();
        task.setStatus(Status.NEW);
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
        prioritizedTasks.add(task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask createSubTask(Subtask subtask) {
        if (subtask != null) {
            prioritizedTasks.add(subtask);
            subtask.setId(createId++);
            subtask.setStatus(Status.NEW);
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            return subtask;
        } else {
            return null;
        }
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
        subtasks.remove(id);
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
    public void deleteTasks() {
        for (Integer removeTask : tasks.keySet()) {
            historyManager.remove(removeTask);
        }
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Integer removeEpic : epics.keySet()) {
            historyManager.remove(removeEpic);
        }
        epics.clear();
        deleteSubtasks();
    }

    @Override
    public void deleteSubtasks() {
        if (!epics.isEmpty()) {
            for (Epic epic : epics.values()) {
                epic.setStatus(Status.NEW);
                updateEpic(epic);
                epic.getIDsOfSubtasks().clear();
            }
            for (Integer removeSubtask : subtasks.keySet()) {
                historyManager.remove(removeSubtask);
            }
        }
        subtasks.clear();
    }
}

