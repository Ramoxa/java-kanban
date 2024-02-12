package tasks;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasks;
    private Instant endTime = Instant.ofEpochSecond(0);
    public Epic(int id, String name, Status status, String description, Instant startTime, long duration) {
        super(name, description, startTime, duration);
        this.endTime = super.getEndTime();
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
        this.status = status;
        this.id = id;
    }

    public Epic(String name, String description) {
        super(name, description, Instant.ofEpochSecond(0), 0);
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public void updateEpicState(Map<Integer, Subtask> subs) {
        if (subtasks.isEmpty()) {
            return;
        }

        Instant startTime = subs.get(subtasks.get(0)).getStartTime();
        Instant endTime = subs.get(subtasks.get(0)).getEndTime();
        int isNew = 0;
        int isDone = 0;
        for (Integer id : getIDsOfSubtasks()) {
            Subtask subtask = subs.get(id);
            if (subtask != null) {
                if (subtask.getStatus() == Status.NEW) isNew += 1;
                if (subtask.getStatus() == Status.DONE) isDone += 1;
                if (subtask.getStartTime().isBefore(startTime)) startTime = subtask.getStartTime();
                if (subtask.getEndTime().isAfter(endTime)) endTime = subtask.getEndTime();
            }
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime).toMinutes();

        if (subtasks.size() == isNew) {
            setStatus(Status.NEW);
            return;
        } else if (subtasks.size() == isDone) {
            setStatus(Status.DONE);
            return;
        }
        setStatus(Status.IN_PROGRESS);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getId());
    }
    public List<Integer> getIDsOfSubtasks() {
        return subtasks;
    }
    public void addSubtaskIds(int id) {
        subtasks.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return getTaskType() == epic.getTaskType() && Objects.equals(subtasks, epic.subtasks) && Objects.equals(getEndTime(), epic.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTaskType(), subtasks, getEndTime());
    }

    @Override
    public String toString() {

        return id + "," + taskType + "," + name + "," + status + "," + description + "," + getStartTime() + "," + duration + "," + getEndTime();

    }
}