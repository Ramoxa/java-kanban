package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

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
            history.add(parseInt(line));

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
        String[] list = {Integer.toString(task.getId()), getType(task).toString(), task.getName(), task.getStatus().toString(), task.getDescription(), String.valueOf(task.getStartTime()), String.valueOf(task.getDuration()), getParentEpicId(task)};
        return String.join(",", list);
    }


    public static Task tasksFromString(String value) {

        int epicID = 0;
        String[] values = value.split(",");
        int id = parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];
        Instant startTime = Instant.parse(values[5]);
        long duration = Long.parseLong(values[6]);

        if (TaskType.valueOf(type).equals(TaskType.SUBTASK)) {
            epicID = parseInt(values[7]);
        }

        switch (TaskType.valueOf(type)) {
            case SUBTASK:
                return new Subtask(id, name, status, description, startTime, duration, epicID);
            case TASK:
                return new Task(id, name, status, description, startTime, duration);
            case EPIC:
                return new Epic(id, name, status, description, startTime, duration);
            default:
                throw new NotSupportedTypeException("Данный формат не поддерживается");
        }

    }

}