package tests;

import manager.taskManagers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Instant;


class InMemoryTaskManagerTest extends TaskManagersTest<InMemoryTaskManager> {
    @BeforeEach
    public void startMethod() {
        taskManager = new InMemoryTaskManager();
        task = new Task("Task", "posolil", Instant.EPOCH, 0);
        task.setId(1);
        taskManager.createTask(task);
        epic = new Epic("epic", "00000");
        epic.setId(2);
        taskManager.createEpic(epic);
        subTask = new Subtask("subtask", "111111", Instant.EPOCH, 30, epic.getId());
        subTask.setId(3);
        taskManager.createSubTask(subTask);
    }
}

