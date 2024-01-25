package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.util.ArrayList;
import java.util.List;

public class Formatter {

    public static String historyToString(HistoryManager manager) {

        StringBuilder sb = new StringBuilder();

        for (Task task : manager.getHistory())
            sb.append(task.getId()).append(",");

        return sb.toString();

    }


    public static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();

        for (var line : value.split(","))
            history.add(Integer.parseInt(line));

        return history;

    }

    private static String getParentEpicId(Task task) {
        if (task instanceof Subtask) {
            return Integer.toString(((Subtask) task).getEpicId());
        }
        return "";
    }

    public static TaskType getType(Task task) {
        if (task instanceof Epic) {
            return TaskType.EPIC;
        } else if (task instanceof Subtask) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    public static String toString(Task task) {
        String[] list = {Integer.toString(task.getId()),
                getType(task).toString(),
                task.getName(),
                task.getStatus().toString(),
                task.getDescription(),
                getParentEpicId(task)};
        return String.join(",", list);
    }


    public static Task tasksFromString(String value) {

        int epicID = 0;
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];

        if (TaskType.valueOf(type).equals(TaskType.SUBTASK)) {
            epicID = Integer.parseInt(values[5]);
        }

        switch (TaskType.valueOf(type)) {
            case SUBTASK:
                return new Subtask(id, name, description, status, epicID);
            case TASK:
                return new Task(id, name, description, status);
            case EPIC:
                return new Epic(id, name, description, status);
            default:
                throw new NotSupportedTypeException("Данный формат не поддерживается");
        }

    }

}