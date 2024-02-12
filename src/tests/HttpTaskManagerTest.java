package tests;

import manager.Managers;
import manager.taskManagers.HttpTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTest extends TaskManagersTest <HttpTaskManager>  {
    KVServer kvServer;
    @BeforeEach
    void startMethod() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        super.taskManager = Managers.getDefault();
        task = new Task("111", "2222",
                Instant.EPOCH, 0);
        task.setId(1);
        taskManager.createTask(task);
        epic = new Epic("3333", "444444");
        epic.setId(2);
        taskManager.createEpic(epic);
        subTask  = new Subtask("555555555", "666666666",
                Instant.EPOCH, 0, epic.getId());
        subTask.setId(3);
        taskManager.createSubTask(subTask);
        Subtask subtask = new Subtask("555555555", "666666666",
                Instant.EPOCH, 0, epic.getId());
        subtask.setId(123);
        taskManager.createSubTask(subtask);
    }

    @AfterEach
    void stop() {
        kvServer.stop();
    }

    @Test
    void load() {
        taskManager.getTask(task.getId());
        taskManager.getTask(epic.getId());
        HttpTaskManager httpTaskManagerLoad = new HttpTaskManager("http://localhost:8078", true);
        assertEquals(taskManager.getTasks().toString(), httpTaskManagerLoad.getTasks().toString());
        assertEquals(taskManager.getSubtasks().toString(), httpTaskManagerLoad.getSubtasks().toString());
        assertEquals(taskManager.getEpics().toString(), httpTaskManagerLoad.getEpics().toString());
        assertEquals(taskManager.getHistory().toString(), httpTaskManagerLoad.getHistory().toString());
        assertEquals(taskManager.getPrioritizedTasks().toString(), httpTaskManagerLoad.getPrioritizedTasks().toString());
    }

    @Test
    public void deleteSubtask() {
        taskManager.createSubTask(subTask);
        taskManager.deleteSubtask(subTask.getId());
        assertFalse(taskManager.getSubtasks().isEmpty());
    }

    @Test
    public void testGetSubtask() {
        taskManager.createSubTask(subTask);
        List<Subtask> tasksList = taskManager.getSubtasks();
        Assertions.assertEquals(3, tasksList.size());
        Task emptyTask = taskManager.getSubtask(123);
        Assertions.assertNull(emptyTask);
        Task invalidTask = taskManager.getSubtask(999);
        Assertions.assertNull(invalidTask);
    }

    @Test
    void addNewSubtask() {
        taskManager.createSubTask(subTask);
        final Subtask savedTask = taskManager.getSubtask(subTask.getId());
        assertNotNull(savedTask);
        assertEquals(subTask, savedTask);
        final List<Subtask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks);
        Assertions.assertEquals(3, subtasks.size());
        Assertions.assertEquals(subTask, subtasks.get(0));
    }



}




