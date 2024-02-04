package tasks;

import java.time.Instant;
import java.util.Objects;

public class Subtask extends Task {
    private final int epicID;

    public Subtask(String name,
                   String description,
                   Instant startTime,
                   long duration,
                   int epicID) {

        super(name, description, startTime, duration);
        this.taskType = TaskType.SUBTASK;
        this.epicID = epicID;

    }

    public Subtask(int id,
                   String name,
                   Status status,
                   String description,
                   Instant startTime,
                   long duration,
                   int epicID) {

        super(name, description, startTime, duration);
        this.taskType = TaskType.SUBTASK;
        this.status = status;
        this.epicID = epicID;
        this.id = id;

    }


    public int getEpicId() {
        return epicID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return getEpicId() == subtask.getEpicId() && getTaskType() == subtask.getTaskType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId(), getTaskType());
    }

    @Override
    public String toString() {
        return id + "," + taskType + "," + name + "," + status + "," + description + "," + getStartTime() + "," + duration + "," + getEndTime();
    }
}