import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public long getEpicId() {
        return epicId;
    }

    public void setEpicID(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return getEpicId() == subtask.getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
    }
}