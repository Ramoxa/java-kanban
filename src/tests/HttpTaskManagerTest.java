package tests;

import manager.Managers;
import manager.taskManagers.HttpTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest  extends TaskManagersTest <HttpTaskManager> {
    KVServer kvServer;
    @BeforeEach
    void bebra() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        super.taskManager = (HttpTaskManager) Managers.getDefault();
        task = new Task("111", "2222",
                Instant.EPOCH, 0);
        task.setId(1);
        taskManager.createTask(task);
        epic = new Epic("3333", "444444");
        epic.setId(2);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("555555555", "666666666",
                Instant.EPOCH, 0, epic.getId());
        subtask.setId(3);
        taskManager.createSubTask(subtask);
    }
    @AfterEach
    void stop() {
        kvServer.stop();
    }
    @Test
    void load() {
        taskManager.getTaskById(task.getId());
        taskManager.getTaskById(epic.getId());
        HttpTaskManager httpTaskManagerLoad = new HttpTaskManager("http://localhost:8078", true);
        assertEquals(taskManager.getTasks().toString(), httpTaskManagerLoad.getTasks().toString());
        assertEquals(taskManager.getSubtasks().toString(), httpTaskManagerLoad.getSubtasks().toString());
        assertEquals(taskManager.getEpics().toString(), httpTaskManagerLoad.getEpics().toString());
        assertEquals(taskManager.getHistory().toString(), httpTaskManagerLoad.getHistory().toString());

        assertEquals(taskManager.getPrioritizedTasks().toString(), httpTaskManagerLoad.getPrioritizedTasks().toString());
    }
}

