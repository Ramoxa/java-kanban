package tests;

import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class HistoryManagerTest {
    protected Task task;
    protected Epic epic;
    protected Subtask subTask;
    private HistoryManager manager;

    @BeforeEach
    public void loadManager() {

        manager = Managers.getDefaultHistory();
    }

    @BeforeEach
    void beforeEach() {
        task = new Task("1", "1111", Instant.EPOCH, 0);
        epic = new Epic("222", "3333");
        subTask = new Subtask("333", "4444", Instant.EPOCH, 0);
    }

    @Test
    public void add() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(task);
        Task task2 = taskManager.createTask(new Task("2", "122", Instant.EPOCH, 0));
        Task task3 = taskManager.createTask(new Task("2", "122", Instant.EPOCH, 0));
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);
        assertEquals(List.of(task, task2, task3), manager.getHistory());
    }

    @Test
    void remove() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(task);
        Task task2 = taskManager.createTask(new Task("2", "122", Instant.EPOCH, 0));
        Task task3 = taskManager.createTask(new Task("2", "122", Instant.EPOCH, 0));
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.remove(task3.getId());
        assertEquals(List.of(task, task2), manager.getHistory());
    }

    @Test
    void getHistory() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(task);
        Task task2 = taskManager.createTask(new Task("2", "122", Instant.EPOCH, 0));
        Task task3 = taskManager.createTask(new Task("2", "122", Instant.EPOCH, 0));
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.remove(task.getId());
        manager.remove(task2.getId());
        manager.remove(task3.getId());
        assertEquals(List.of(), manager.getHistory());
    }


}

