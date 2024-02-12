package manager.taskManagers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import manager.Managers;
import server.KVTaskClient;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private KVTaskClient taskClient;
    private final Gson gson = new GsonBuilder().create();
    private final String url;

    public HttpTaskManager(String url) {
        this(url, false);
    }

    public HttpTaskManager(String url, boolean isLoad) {
        super(null);
        this.url = url;
        taskClient = new KVTaskClient(url);
        if (isLoad) {
            load();
        }
    }

    @Override
    protected void save() {
        List<Task> taskList = new ArrayList<>(tasks.values());
        String taskJson = gson.toJson(taskList);
        taskClient.put("tasks", taskJson);

        List<Epic> epicList = new ArrayList<>(epics.values());
        String epicJson = gson.toJson(epicList);
        taskClient.put("epics", epicJson);

        List<Subtask> subtaskList = new ArrayList<>(subtasks.values());
        String subtaskJson = gson.toJson(subtaskList);
        taskClient.put("subtasks", subtaskJson);

        List<Task> historyList = historyManager.getHistory();
        List<Integer> historyIds = historyList.stream()
                .map(Task::getId)
                .collect(Collectors.toList());
        String historyJson = gson.toJson(historyIds);
        taskClient.put("history", historyJson);
    }

    protected void load() {
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> taskList = gson.fromJson(taskClient.load("tasks"), taskListType);
        for (Task task : taskList) {
            int taskId = task.getId();
            if (taskId > createId) {
                createId = taskId;
            }
            tasks.put(taskId, task);
            prioritizedTasks.add(task);
        }

        Type epicListType = new TypeToken<List<Epic>>() {}.getType();
        List<Epic> epicList = gson.fromJson(taskClient.load("epics"), epicListType);
        for (Epic epic : epicList) {
            int epicId = epic.getId();
            if (epicId > createId) {
                createId = epicId;
            }
            epics.put(epicId, epic);
        }

        Type subtaskListType = new TypeToken<List<Subtask>>() {}.getType();
        List<Subtask> subtaskList = gson.fromJson(taskClient.load("subtasks"), subtaskListType);
        for (Subtask subtask : subtaskList) {
            int subtaskId = subtask.getId();
            if (subtaskId > createId) {
                createId = subtaskId;
            }
            subtasks.put(subtaskId, subtask);
            prioritizedTasks.add(subtask);
        }

        Type historyListType = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> historyList = gson.fromJson(taskClient.load("history"), historyListType);
        for (Integer taskId : historyList) {
            if (tasks.containsKey(taskId)) {
                historyManager.addTask(tasks.get(taskId));
            } else if (subtasks.containsKey(taskId)) {
                historyManager.addTask(subtasks.get(taskId));
            } else {
                historyManager.addTask(epics.get(taskId));
            }
        }
    }

}
