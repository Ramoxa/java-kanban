package inside;

import manager.Status;
import manager.TaskType;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList subtasksId;

    private final TaskType taskType;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksId = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.subtasksId = new ArrayList<>();
        this.taskType = TaskType.EPIC;
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
        return Objects.equals(subtasksId, epic.subtasksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksId);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksId=" + subtasksId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

}