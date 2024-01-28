package tests;
import inside.Epic;
import inside.Subtask;
import inside.Task;
import manager.InMemoryTaskManager;
import manager.Status;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static manager.InMemoryTaskManager.historyManager;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class HistoryManagerTest {
    protected Task task;
    protected Epic epic;
    protected Subtask subTask;


    @BeforeEach
    public void beforeEach() {
        task = new Task("1", "1111", Instant.EPOCH, 0);
        epic = new Epic("222", "3333");
        subTask = new Subtask("333", "4444", Instant.EPOCH, 0);
    }

    @Test
    public void add() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createTask(task);

        List<Task> tasksList = historyManager.getHistory();
        Assertions.assertEquals(1, tasksList.size());

        Task emptyTask = taskManager.getTask(123);
        Assertions.assertNull(emptyTask);

        Task invalidTask = taskManager.getTask(999);
        Assertions.assertNull(invalidTask);
    }

    @Test
    void removeFromHead(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createTask(task);
        historyManager.addTask(task);
        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history);
        assertEquals(0, history.size());
    }


}

