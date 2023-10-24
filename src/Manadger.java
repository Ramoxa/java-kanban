import java.util.HashMap;

public class Manadger {

    private int createId;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
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
        task.setStatus(Status.DONE);
        tasks.remove(task.getId(), task);
        return task;
    }
    public void updateTask(Task task) {
        task.setStatus(Status.IN_PROGRESS);
        long id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
        System.out.println("Обновлено");
    }

    public void updateEpic(Epic epic) {
        epic.setStatus(Status.IN_PROGRESS);
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Обновлено");
    }

    public void updateSubtask(Subtask subtask) {
        subtask.setStatus(Status.IN_PROGRESS);
        long id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        System.out.println("Обновлено");
    }
    public void printAllTasks(HashMap<Integer, Task> tasks) {
        for (Integer id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println(id + " " + value);
        }
        if (tasks.isEmpty()) {
            System.out.println("Задач нет.");
        }
    }
    public void printAllEpics(HashMap<Integer, Epic> epics) {
        for (Integer id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println(id + " " + value);
        }
        if (epics.isEmpty()) {
            System.out.println("Задач нет");
        }
    }
    public void printAllSubtasks(HashMap<Integer, Subtask> subtasks) {
        for (Integer id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println(id + " " + value);
        }
        if (subtasks.isEmpty()) {
            System.out.println("Задач нет");
        }
    }
}

