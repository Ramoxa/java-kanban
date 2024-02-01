package tasks;

import java.time.Instant;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;


    public Subtask(String name,
                   String description,
                   Instant startTime,
                   long duration
                   ) {

        super(name, description, startTime, duration);
        this.status = Status.NEW;
        this.taskType = TaskType.SUBTASK;
        this.startTime = startTime;
        this.duration = duration;
        this.name = name;

    }

    public Subtask(int id, String name, Status status, String description, Instant startTime,
                   long duration, int epicId) {

        super(name, description, startTime, duration);
        this.taskType = TaskType.SUBTASK;
        this.status = status;
        this.epicId = epicId;
        this.id = id;

    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
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