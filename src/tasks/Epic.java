package tasks;

import manager.Status;
import manager.TaskType;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtasksId;
    private Instant endTime = Instant.ofEpochSecond(0);

    public Epic(int id, String name, Status status, String description, Instant startTime, long duration) {

        super(name, description, startTime, duration);
        this.endTime = super.getEndTime();
        this.subtasksId = new ArrayList<>();
        this.taskType = TaskType.EPIC;
        this.status = status;
        this.id = id;

    }
    public Epic(String name, String description) {
        super(name, description, Instant.ofEpochSecond(0), 0);
        this.subtasksId = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public void updateEpicState(Map<Integer, Subtask> subs) {

        Instant startTime = subs.get(subtasksId.get(0)).getStartTime();
        Instant endTime = subs.get(subtasksId.get(0)).getEndTime();

        int isNew = 0;
        int isDone = 0;

        for (Integer id : getIDsOfSubtasks(id)) {
            Subtask subtask = subs.get(id);

            if (subtask.getStatus() == Status.NEW) isNew += 1;

            if (subtask.getStatus() == Status.DONE) isDone += 1;

            if (subtask.getStartTime().isBefore(startTime)) startTime = subtask.getStartTime();

            if (subtask.getEndTime().isAfter(endTime)) endTime = subtask.getEndTime();
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime).toMinutes();

        if (getIDsOfSubtasks(id).size() == isNew) {
            setStatus(Status.NEW);
            return;

        } else if (getIDsOfSubtasks(id).size() == isDone) {
            setStatus(Status.DONE);
            return;
        }

        setStatus(Status.IN_PROGRESS);

    }


    public ArrayList<Integer> getIDsOfSubtasks(int id) {
        return subtasksId;
    }

    public void setIDsOfSubtasks(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return getTaskType() == epic.getTaskType() && Objects.equals(subtasksId, epic.subtasksId) && Objects.equals(getEndTime(), epic.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTaskType(), subtasksId, getEndTime());
    }

    @Override
    public String toString() {

        return id + "," + taskType + "," + name + "," + status + "," + description + "," + getStartTime() + "," + duration + "," + getEndTime();

    }
}