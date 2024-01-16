package inside;

import manager.Status;
import manager.TaskType;

import java.util.Objects;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    protected TaskType taskType;


    public Task(String name,
                String description) {

        this.description = description;
        this.name = name;
    }


    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = taskType;
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
    public String toString() {
        return "Task{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", status=" + status + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() && Objects.equals(getName(), task.getName()) && Objects.equals(getDescription(), task.getDescription()) && Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getStatus());
    }


}
