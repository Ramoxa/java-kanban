package tests;

import manager.taskManagers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.Status;

import tasks.Epic;

import java.util.List;
import java.util.Objects;

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
        List<Epic> epicList = taskManager.getEpicsByStatus(Status.NEW);
        assertEquals(2, epicList.size());
    }

    @Test
    public void testAllDoneEpics() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("аа", "аа");
        taskManager.createEpic(epic);
        epic.setStatus(Status.DONE);
        taskManager.updateEpic(epic);
        List<Epic> epicList = taskManager.getEpicsByStatus(Status.DONE);
        assertEquals(1, epicList.size());
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

        if (epic2 == taskManager.getEpicsByStatus(Status.DONE) && Objects.equals(epic1, taskManager.getEpicsByStatus(Status.NEW))) {
            List<Epic> epicList = taskManager.getEpics();
            assertEquals(2, epicList.size());
        }
    }

    @Test
    public void testInProgressEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("аа", "аа");
        taskManager.createEpic(epic);
        epic.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpic(epic);
        List<Epic> epicList = taskManager.getEpicsByStatus(Status.IN_PROGRESS);
        assertEquals(1, epicList.size());
    }


}