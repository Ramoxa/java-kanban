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
    protected HistoryManager historyManager;
    protected int createId = 0;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
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
            epic.setStatus(Status.IN_PROGRESS);
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
        if (subtasks.containsKey(id)) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            if (epic != null) {
                List<Integer> subtaskIDs = epic.getIDsOfSubtasks();
                if (subtaskIDs.contains(id)) {
                    subtaskIDs.remove((Integer) id);
                    updateEpic(epic);
                }
            }
            subtasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (Integer epicId : epic.getIDsOfSubtasks()) {
                subtasks.remove(epicId);
            }
            epics.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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

    private int getNextID() {
        return ++createId;
    }
}

