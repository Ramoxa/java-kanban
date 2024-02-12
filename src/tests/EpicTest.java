package tests;

import manager.taskManagers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EpicTest {

    @Test
    public void testEmptyEpicList() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        List<Epic> epicList = taskManager.getEpics();
        assertEquals(0, epicList.size());
    }

    @Test
    public void testAllNewEpics() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("аа", "аа");
        Epic epic2 = new Epic("аа", "аа");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        List<Epic> epicList = taskManager.getEpics();
        assertEquals(2, epicList.size());
    }

    @Test
    public void testInProgressEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("аа", "аа");
        taskManager.createEpic(epic1);
        epic1.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);
        assertEquals(Status.IN_PROGRESS, epic1.getStatus());
    }

    @Test
    public void testAllDoneEpics() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("аа", "аа");
        taskManager.createEpic(epic);
        epic.setStatus(Status.DONE);
        taskManager.updateEpic(epic);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void testMixedStatusEpics() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("аа", "аа");
        Epic epic2 = new Epic("аа", "аа");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        epic1.setStatus(Status.NEW);
        epic2.setStatus(Status.DONE);
        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);
        assertEquals(Status.NEW, epic1.getStatus());
        assertEquals(Status.DONE, epic2.getStatus());
    }
}
