package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.util.ArrayList;
import java.util.List;

public class Formatter {

    public static String historyToString(HistoryManager manager) {

        StringBuilder q = new StringBuilder();

        for (Task task : manager.getHistory())
            q.append(task.getId()).append(",");

        return q.toString();

    }

    public static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();

        for (var line : value.split(","))
            history.add(Integer.parseInt(line));

        return history;

    }

    public static String tasksToString(TaskManager tasksManager) {

        List<Task> allTasks = new ArrayList<>();
        var result = new StringBuilder();

        allTasks.addAll(tasksManager.getTasks());
        allTasks.addAll(tasksManager.getEpics());
        allTasks.addAll(tasksManager.getSubtasks());

        for (var task : allTasks)
            result.append(task.toString()).append("\n");

        return result.toString();

    }

    public static Task tasksFromString(String value) {

        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];
        int epicID = 0;

        if (TaskType.valueOf(type).equals(TaskType.SUBTASK)) epicID = Integer.parseInt(values[5]);

        if (TaskType.valueOf(type).equals(TaskType.TASK)) return new Task(id, name, description, status);

        if (TaskType.valueOf(type).equals(TaskType.EPIC)) return new Epic(id, name, description, status);

        if (TaskType.valueOf(type).equals(TaskType.SUBTASK)) return new Subtask(id, name, description, status, epicID);

        else throw new NotSupportedTypeException("Данный формат не поддерживается");
    }

}
