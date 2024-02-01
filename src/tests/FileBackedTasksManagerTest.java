package tests;

import manager.taskManagers.FileBackedTasksManager;
import org.junit.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.Instant;

import static org.junit.Assert.assertEquals;


public class FileBackedTasksManagerTest extends TaskManagersTest<FileBackedTasksManager> {

    @Test
    public void testLoad() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("test.txt"));
        Task task1 = fileBackedTasksManager.createTask(new Task("1", "1111", Instant.EPOCH, 0));
        Task task2 = fileBackedTasksManager.createTask(new Task("22", "22222", Instant.EPOCH, 0));
        Epic epic1 = fileBackedTasksManager.createEpic(new Epic("1", "7777"));
        Epic epic2 = fileBackedTasksManager.createEpic(new Epic("6666", "7777"));
        Subtask subtask1 = fileBackedTasksManager.createSubTask(new Subtask("3333", "44444", Instant.EPOCH, 0));
        Subtask subtask2 = fileBackedTasksManager.createSubTask(new Subtask("5555", "55555", Instant.EPOCH, 0));

        assertEquals(2, fileBackedTasksManager.getTasks().size());
        assertEquals(2, fileBackedTasksManager.getSubtasks().size());
        assertEquals(2, fileBackedTasksManager.getEpics().size());
        assertEquals(0, fileBackedTasksManager.getHistory().size());

        FileBackedTasksManager manager1 = FileBackedTasksManager.loadFromFile(new File("test.txt"));
        assertEquals(2, manager1.getTasks().size());
        assertEquals(2, manager1.getSubtasks().size());
        assertEquals(2, manager1.getEpics().size());
        assertEquals(0, manager1.getHistory().size());

    }

    @Test
    public void testLoadNoSubtask() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("test.txt"));
        Task task1 = fileBackedTasksManager.createTask(new Task("1", "1111", Instant.EPOCH, 0));
        Task task2 = fileBackedTasksManager.createTask(new Task("22", "22222", Instant.EPOCH, 0));
        Epic epic1 = fileBackedTasksManager.createEpic(new Epic("1", "7777"));
        Epic epic2 = fileBackedTasksManager.createEpic(new Epic("6666", "7777"));

        assertEquals(2, fileBackedTasksManager.getTasks().size());
        assertEquals(0, fileBackedTasksManager.getSubtasks().size());
        assertEquals(2, fileBackedTasksManager.getEpics().size());

        FileBackedTasksManager manager1 = FileBackedTasksManager.loadFromFile(new File("test.txt"));
        manager1.load();
        assertEquals(2, manager1.getTasks().size());
        assertEquals(0, manager1.getSubtasks().size());
        assertEquals(2, manager1.getEpics().size());

    }


}


