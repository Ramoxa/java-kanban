package inside;

import manager.Status;
import manager.TaskType;

import java.time.Instant;
import java.util.Objects;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TaskType taskType;
    protected Instant startTime;
    protected long duration;
    public Task(String name,
                String description,
                Instant startTime,
                long duration) {

        this.status = Status.NEW;
        this.description = description;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
        this.name = name;

    }

    public Task(int id,
                String name,
                Status status,
                String description,
                Instant startTime,
                long duration) {

        this.description = description;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.status = status;
        this.duration = duration;
        this.name = name;
        this.id = id;

    }

    public Instant getStartTime() {
        return startTime;
    }


    public Instant getEndTime() {
        final byte SECONDS_IN_ONE_MINUTE = 60;
        return startTime.plusSeconds(duration * SECONDS_IN_ONE_MINUTE);

    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() && getDuration() == task.getDuration() && Objects.equals(getName(), task.getName()) && Objects.equals(getDescription(), task.getDescription()) && getStatus() == task.getStatus() && getTaskType() == task.getTaskType() && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getStatus(), getTaskType(), startTime, getDuration());
    }

    @Override
    public String toString() {

        return id + ","
                + taskType + ","
                + name + ","
                + status + ","
                + description + ","
                + getStartTime() + ","
                + duration + ","
                + getEndTime();

    }
}
