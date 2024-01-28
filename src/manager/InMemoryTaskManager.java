package manager;


import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class InMemoryTaskManager implements TaskManager {


    public static HistoryManager historyManager;
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected int createId = 1;
    private Set<Task> prioritizedTasks;


    public InMemoryTaskManager(HistoryManager historyManager) {
        InMemoryTaskManager.historyManager = historyManager;
    }


    public int getNextID() {
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

    @Override // сравнение тасков по getStartTime()
    public int compare(Task o1, Task o2) {

        return o1.getStartTime().compareTo(o2.getStartTime());

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

    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        subtask.setStatus(Status.NEW);
        epic.getIDsOfSubtasks(subtask.getId()).add(subtask.getId());
        subtask.setEpicId(epic.getId());
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
}